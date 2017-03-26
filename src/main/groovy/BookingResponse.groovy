import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.annotation.JsonSerialize

import java.text.SimpleDateFormat

class BookingResponse {
    @JsonSerialize(using = BookingCalendarSerializer.class)
    Map<Calendar, List<Booking>> calendar

    static class Booking {
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
        Calendar begin
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
        Calendar end
        String employerId

        Booking() {

        }

        Booking(BookingRequest.Request request) {
            begin = request.meetingStartTime
            end = request.meetingStartTime.clone() as Calendar
            end.add(Calendar.HOUR, request.meetingDuration)
            employerId = request.employerId
        }
    }

    static class BookingCalendarSerializer extends JsonSerializer<Map<Calendar, List<Booking>>> {

        @Override
        void serialize(Map<Calendar, List<Booking>> value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
            gen.writeStartObject()
            value.each {
                def format = new SimpleDateFormat("yyyy-MM-dd")
                def time = format.format(it.key.time)
                gen.writeObjectField(time, it.value)
            }
            gen.writeEndObject()
        }
    }
}
