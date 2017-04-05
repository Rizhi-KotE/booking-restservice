package rk.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime

class BookingResponse {
    @JsonFormat(pattern = "yyyy-MM-dd")
    LinkedMultiValueMap<LocalDate, Booking> calendar
}
