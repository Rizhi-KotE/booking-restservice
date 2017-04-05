package rk.service

import rk.dto.BookingRequest
import rk.dto.BookingResponse

interface BookingService {
    BookingResponse calculateBooking(BookingRequest bookingRequest)
}