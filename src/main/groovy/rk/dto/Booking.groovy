package rk.dto

import com.fasterxml.jackson.annotation.JsonFormat
import groovy.transform.Canonical

import java.time.LocalTime

@Canonical
class Booking {
    @JsonFormat(pattern = "HH:mm")
    LocalTime begin
    @JsonFormat(pattern = "HH:mm")
    LocalTime end
    String employerId
}
