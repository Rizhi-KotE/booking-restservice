package rk.dto

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.util.LinkedMultiValueMap

import java.time.LocalDate

/**
 * DTO that represents output message
 */
class Calendar {
    @JsonFormat(pattern = "yyyy-MM-dd")
    LinkedMultiValueMap<LocalDate, CalendarEntry> calendar
}
