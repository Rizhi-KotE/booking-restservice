import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.json.JsonTest
import org.springframework.boot.test.json.JacksonTester
import org.springframework.core.io.ClassPathResource
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.util.LinkedMultiValueMap
import rk.BookingServiceApplication
import rk.dto.Booking
import rk.dto.BookingRequest
import rk.dto.BookingResponse

import java.time.LocalDate
import java.time.LocalTime

import static java.time.Month.MARCH
import static org.assertj.core.api.Assertions.assertThat
import static org.junit.Assert.assertEquals

@RunWith(SpringRunner.class)
@JsonTest
@ContextConfiguration(classes = BookingServiceApplication.class)
class SerializationTest {

    @Autowired
    ObjectMapper mapper

    @Test
    void deserializeRequest() {
        def request = mapper.readValue(new ClassPathResource("requestExample.json").getURL(),
                BookingRequest.class)

        assertEquals(9, request.officeHoursBegin.getHour())
        assertEquals(0, request.officeHoursBegin.getMinute())
        assertEquals(17, request.officeHoursEnd.getHour())
        assertEquals(30, request.officeHoursEnd.getMinute())

        def request1 = request.requests[0]

        assertEquals('EMP1', request1.employerId)
        assertEquals(2, request1.meetingDuration)

        def submitDate = request1.submitDate
        assertEquals(2011, submitDate.getYear())
        assertEquals(MARCH, submitDate.getMonth())
        assertEquals(17, submitDate.getDayOfMonth())
        assertEquals(10, submitDate.getHour())
        assertEquals(17, submitDate.getMinute())
        assertEquals(06, submitDate.getSecond())

        def startTime = request1.meetingStartTime
        assertEquals(2011, startTime.getYear())
        assertEquals(MARCH, startTime.getMonth())
        assertEquals(21, startTime.getDayOfMonth())
        assertEquals(9, startTime.getHour())
        assertEquals(00, startTime.getMinute())
    }

    @Autowired
    JacksonTester<BookingResponse> json

    @Test
    void serializationResponseTest() throws Exception {
        def map = [(LocalDate.of(2011, MARCH, 21)): [
                new Booking(begin: LocalTime.of(9, 0),
                        end: LocalTime.of(11, 0), employerId: 'IMP1')]]
        def calendar = new LinkedMultiValueMap<>(map)
        def response = new BookingResponse(calendar: calendar)

        assertThat(json.write(response)).isEqualToJson('serializationResponseTest.json')
    }
}
