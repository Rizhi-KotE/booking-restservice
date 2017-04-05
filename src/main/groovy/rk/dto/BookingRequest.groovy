package rk.dto

import com.fasterxml.jackson.annotation.JsonFormat

import java.time.LocalDateTime
import java.time.LocalTime

class BookingRequest {
    @JsonFormat(pattern = "HHmm")
    LocalTime officeHoursBegin
    @JsonFormat(pattern = "HHmm")
    LocalTime officeHoursEnd

    List<Request> requests
}
