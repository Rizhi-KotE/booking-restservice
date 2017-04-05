package rk.dto

import com.fasterxml.jackson.annotation.JsonFormat
import groovy.transform.Canonical
import org.hibernate.validator.constraints.NotEmpty

import javax.validation.constraints.AssertTrue
import javax.validation.constraints.NotNull
import java.time.LocalDateTime

@Canonical
class BookingRequestEntry {
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime submitDate
    @NotNull
    @NotEmpty
    String employerId
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @NotNull
    LocalDateTime meetingStartTime

    int meetingDuration

    @AssertTrue
    boolean isMeetingDurationMoreThanZero() {
        meetingDuration > 0
    }

    @AssertTrue
    boolean isMeetingStartAfterSubmit() {
        meetingStartTime > submitDate
    }
}
