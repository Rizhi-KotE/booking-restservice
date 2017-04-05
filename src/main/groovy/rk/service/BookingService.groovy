package rk.service

import rk.dto.BookingRequest
import rk.dto.Calendar

/**
 Service that should calculate meeting calendar

 Constraints / Notes
 • No part of a meeting may fall outside office hours.
 • Meetings may not overlap.
 • The booking submission system only allows one submission at a time, so submission
 times are guaranteed to be unique.
 • Bookings must be processed in the chronological order in which they were submitted.
 • The ordering of booking submissions in the supplied input is not guaranteed.
 • The current requirements make no provision for alerting users of failed bookings; it is
 up to the user to confirm that their booking was successful.
 */
interface BookingService {
    Calendar calculateBooking(BookingRequest bookingRequest)
}