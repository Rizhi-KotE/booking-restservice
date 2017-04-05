package rk.dto

import com.fasterxml.jackson.annotation.JsonFormat
import groovy.transform.Canonical

import java.time.LocalDateTime

@Canonical
class BookingRequestEntry {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime submitDate
    String employerId
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime meetingStartTime
    int meetingDuration
}
