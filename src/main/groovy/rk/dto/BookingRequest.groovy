package rk.dto

import com.fasterxml.jackson.annotation.JsonFormat

import javax.validation.Valid
import javax.validation.constraints.AssertFalse
import javax.validation.constraints.AssertTrue
import javax.validation.constraints.NotNull
import java.time.LocalTime

/**
 * DTO that represents input message
 */
class BookingRequest {
    @NotNull
    @JsonFormat(pattern = "HHmm")
    LocalTime officeHoursBegin
    @NotNull
    @JsonFormat(pattern = "HHmm")
    LocalTime officeHoursEnd

    @Valid
    @NotNull
    List<BookingRequestEntry> requests

    @AssertTrue(message = "a property must be eclusively system or owned")
    boolean isOfficeHourBeginLessThanEnd() {
        officeHoursBegin < officeHoursEnd
    }
}
