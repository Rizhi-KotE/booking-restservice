package rk.service

import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import rk.dto.Booking
import rk.dto.BookingRequest
import rk.dto.BookingResponse
import rk.dto.Request

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Component
@Service
class BookingServiceImpl implements BookingService {

    Comparator<Request> comp = { req1, req2 ->
        req1.submitDate <=> req2.submitDate
    }

    BookingResponse calculateBooking(BookingRequest bookingRequest) {

        def filteredRequests = excludeRequestsWhichPartFallOutsideOfficeHours(bookingRequest.requests,
                bookingRequest.officeHoursBegin,
                bookingRequest.officeHoursEnd)
        def filteredOfOverlaps = excludeOverlappedRequests(filteredRequests)

        MultiValueMap<LocalDate, Booking> map = new LinkedMultiValueMap<>()
        for (Request req : filteredOfOverlaps) {
            map.add(req.meetingStartTime.toLocalDate(), convertRequestToBooking(req))
        }
        new BookingResponse(calendar: map)
    }

    ArrayList<Request> excludeRequestsWhichPartFallOutsideOfficeHours(List<Request> requests, LocalTime beginTime, LocalTime endTime) {
        List<Request> sortedFilteredRequest = new ArrayList<>(requests.size())
        for (Request req : requests) {
            def meetingTime = req.meetingStartTime.toLocalTime()
            if (beginTime > meetingTime) continue
            def meetingEndTime = req.meetingStartTime.plusHours(req.meetingDuration).toLocalTime()
            if (endTime < meetingEndTime) continue
            sortedFilteredRequest.add(req)
        }
        sortedFilteredRequest
    }

    Booking convertRequestToBooking(Request request) {
        new Booking(request.meetingStartTime.toLocalTime(),
                request.meetingStartTime.plusHours(request.meetingDuration).toLocalTime(),
                request.employerId)
    }

    List<Request> excludeOverlappedRequests(List<Request> requests) {
        def sortedMap = new TreeMap<LocalDateTime, Request>()
        requests.sort(comp)
        for (Request req : requests) {
            def higherKey = sortedMap.higherKey(req.meetingStartTime)
            if (higherKey != null) {
                def endOfMeeting = req.meetingStartTime.plusHours(req.meetingDuration)
                if (higherKey < endOfMeeting) continue
            }
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
