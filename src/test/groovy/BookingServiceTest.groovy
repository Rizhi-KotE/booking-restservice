import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Before
import org.junit.Test

import static org.junit.Assert.assertEquals
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals

class BookingServiceTest {
    BookingRequest request
    BookingResponse expected

    BookingService service

    @Before
    void setUp() throws Exception {
        service = new BookingService()
    }

    void initTest(String filesName) {
        def mapper = new ObjectMapper()
        def loader = getClass().getClassLoader()
        request = mapper.readValue(loader.getResource(filesName + "Source.json"), BookingRequest.class)
        expected = mapper.readValue(loader.getResource(filesName + "Expected.json"), BookingResponse.class)
    }

    @Test
    void testSerialization() throws Exception {
        initTest("calc")
        assertEquals(9, request.officeHoursBegin.get(Calendar.HOUR_OF_DAY))
        assertEquals(17, request.officeHoursEnd.get(Calendar.HOUR_OF_DAY))
        assertEquals(30, request.officeHoursEnd.get(Calendar.MINUTE))

        assertEquals(2011, request.requests[0].submitDate.get(Calendar.YEAR))
        assertEquals(Calendar.MARCH, request.requests[0].submitDate.get(Calendar.MONTH))
        assertEquals(17, request.requests[0].submitDate.get(Calendar.DATE))
        assertEquals(06, request.requests[0].submitDate.get(Calendar.SECOND))

        def date = expected.calendar.keySet().asList()[0]
        assertEquals(2011, date.get(Calendar.YEAR))
        assertEquals(Calendar.MARCH, date.get(Calendar.MONTH))
        assertEquals(21, date.get(Calendar.DATE))
    }

    @Test
    void taskExample() throws Exception {
        initTest("taskExample")
        def response = service.calcCalendar(request)
        assertReflectionEquals(expected, response)
    }

    @Test
    void testRequestResponseAdapter() throws Exception {
        def request = new BookingRequest.Request(employerId: "EMP",
                meetingDuration: 3,
                meetingStartTime: new Date(10, 10, 1, 5, 9).toCalendar())
        def booking = new BookingResponse.Booking(request)
        assertEquals(new Date(10, 10, 1, 8, 9).toCalendar(), booking.end)
        assertEquals("EMP", booking.employerId)
    }

    @Test
    void simple() throws Exception {
        initTest("simple")
        def response = service.calcCalendar(request)
        assertEquals(1, response.calendar.size())
        assertEquals(1, response.calendar.values().flatten().size())
        assertEquals("EMP001", response.calendar.values().flatten()[0].employerId)
    }

    @Test
    void testOutputFormat() throws Exception {
        initTest("taskExample")
        def response = service.calcCalendar(request)
        def mapper = new ObjectMapper()
        def string = mapper.writeValueAsString(response)
        def expectedJsonFile = getClass().getClassLoader().getResourceAsStream("taskExampleExpected.json")
                .readLines()
                .collect {it.replaceAll("[\\t\\s]+", "")}
                .join("")
        assertEquals(expectedJsonFile, string)

        def requestJsonFile = mapper.writeValueAsString(request)
        def expectedRequestJsonFile = getClass().getClassLoader().getResourceAsStream("taskExampleSource.json")
                .readLines()
                .collect {it.replaceAll("[\\t\\s]{2,}", "")}
                .join("")
        assertEquals(expectedRequestJsonFile, requestJsonFile)
    }
}