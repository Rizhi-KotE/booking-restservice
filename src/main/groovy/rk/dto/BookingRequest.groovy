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

    static class Request {
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime submitDate
        String employerId
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime meetingStartTime
        int meetingDuration
    }
}
