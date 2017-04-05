package rk.service

import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import rk.dto.Booking
import rk.dto.BookingRequest
import rk.dto.BookingResponse

import java.time.LocalDate

@Component
@Service
class BookingService {

    BookingResponse calculateBooking(BookingRequest bookingRequest) {
        MultiValueMap<LocalDate, Booking> map = new LinkedMultiValueMap<>()
        for (BookingRequest.Request request : bookingRequest.requests) {
            map.add(request.meetingStartTime.toLocalDate(), convertRequestToBooking(request))
        }
        new BookingResponse(calendar: map)
    }

    Booking convertRequestToBooking(BookingRequest.Request request) {
        new Booking(request.meetingStartTime.toLocalTime(),
                request.meetingStartTime.plusHours(request.meetingDuration).toLocalTime(),
                request.employerId)
    }
}
