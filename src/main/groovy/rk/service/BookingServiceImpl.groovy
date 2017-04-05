package rk.service

import groovy.transform.PackageScope
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import rk.dto.BookingRequest
import rk.dto.BookingRequestEntry
import rk.dto.Calendar
import rk.dto.CalendarEntry

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Component
@Service
class BookingServiceImpl implements BookingService {

    Calendar calculateBooking(BookingRequest bookingRequest) {

        def filteredRequests = excludeFallOutsideRequests(bookingRequest.requests,
                bookingRequest.officeHoursBegin,
                bookingRequest.officeHoursEnd)
        def filteredOfOverlaps = excludeOverlappedRequests(filteredRequests)

        def map = groupedByMeetingDate(filteredOfOverlaps)
        new Calendar(calendar: map)
    }

    @PackageScope
    LinkedMultiValueMap<LocalDate, CalendarEntry> groupedByMeetingDate(
            List<BookingRequestEntry> filteredOfOverlaps) {

        MultiValueMap<LocalDate, CalendarEntry> map = new LinkedMultiValueMap<>()
        for (BookingRequestEntry req : filteredOfOverlaps) {
            map.add(req.meetingStartTime.toLocalDate(), convertRequesEntryToCalendarEntry(req))
        }
        map
    }

    /**
     * Exclude requests which can't fit in office hours
     *
     * @param requests - requests that should be filtered
     * @param beginTime - begin of office hours
     * @param endTime - end of office hours
     * @return - filtered list of requests
     */
    @PackageScope
    ArrayList<BookingRequestEntry> excludeFallOutsideRequests(List<BookingRequestEntry> requests,
                                                              LocalTime beginTime,
                                                              LocalTime endTime) {

        List<BookingRequestEntry> sortedFilteredRequest = new ArrayList<>(requests.size())
        for (BookingRequestEntry req : requests) {
            def meetingTime = req.meetingStartTime.toLocalTime()
            if (beginTime > meetingTime) continue
            def meetingEndTime = req.meetingStartTime.plusHours(req.meetingDuration).toLocalTime()
            if (endTime < meetingEndTime) continue
            sortedFilteredRequest.add(req)
        }
        sortedFilteredRequest
    }

    @PackageScope
    CalendarEntry convertRequesEntryToCalendarEntry(BookingRequestEntry request) {

        new CalendarEntry(request.meetingStartTime.toLocalTime(),
                request.meetingStartTime.plusHours(request.meetingDuration).toLocalTime(),
                request.employerId)
    }

    @PackageScope
    Comparator<BookingRequestEntry> compBySubmitDate = { req1, req2 ->

        req1.submitDate <=> req2.submitDate
    }

    /**
     * Exclude overlapped requests
     *
     * @param requests
     * @return
     */
    @PackageScope
    List<BookingRequestEntry> excludeOverlappedRequests(List<BookingRequestEntry> requests) {

        //sort request list by submit time because of constraint
        //'Bookings must be processed in the chronological order in which they were submitted.'
        requests.sort(compBySubmitDate)
        //Use sorted map because of need previous and following meetings.
        // This values uses to calculate overlay
        def sortedMap = new TreeMap<LocalDateTime, BookingRequestEntry>()
        for (BookingRequestEntry req : requests) {
            //Need only meeting start time of following meeting
            def higherKey = sortedMap.higherKey(req.meetingStartTime)
            if (higherKey != null) {
                def endOfMeeting = req.meetingStartTime.plusHours(req.meetingDuration)
                if (higherKey < endOfMeeting) continue
            }
            //Need duration of previous meeting
            def lowerEntry = sortedMap.lowerEntry(req.meetingStartTime)
            if (lowerEntry != null) {
                def endOfLowerEntry = lowerEntry.key.plusHours(lowerEntry.value.meetingDuration)
                if (req.meetingStartTime < endOfLowerEntry) continue
            }
            sortedMap.put(req.meetingStartTime, req)
        }
        new ArrayList<>(sortedMap.values())
    }
}
