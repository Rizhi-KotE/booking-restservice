package rk.dto

import com.fasterxml.jackson.annotation.JsonFormat
import groovy.transform.Canonical

import java.time.LocalTime

/**
 *
 */
@Canonical
class CalendarEntry {
    @JsonFormat(pattern = "HH:mm")
    LocalTime begin
    @JsonFormat(pattern = "HH:mm")
    LocalTime end
    String employerId
}
