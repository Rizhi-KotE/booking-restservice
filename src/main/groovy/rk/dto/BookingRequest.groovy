package rk.dto

import com.fasterxml.jackson.annotation.JsonFormat

import java.time.LocalTime

/**
 * DTO that represents input message
 */
class BookingRequest {
    @JsonFormat(pattern = "HHmm")
    LocalTime officeHoursBegin
    @JsonFormat(pattern = "HHmm")
    LocalTime officeHoursEnd

    List<BookingRequestEntry> requests
}
