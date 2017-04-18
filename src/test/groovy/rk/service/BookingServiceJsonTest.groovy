package rk.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import rk.BookingServiceApplication;
import rk.dto.BookingRequest;
import rk.dto.Calendar

import static org.assertj.core.api.Assertions.assertThat

@RunWith(SpringRunner.class)
@JsonTest
@ContextConfiguration(classes = BookingServiceApplication.class)
class BookingServiceJsonTest {

    @Autowired
    JacksonTester<BookingRequest> jsonRequest

    @Autowired
    JacksonTester<Calendar> jsonResponce

    BookingService service = new BookingServiceImpl()

    @Test
    void shouldArrangeAllBookingsIfAllRight() throws Exception {
        def request = jsonRequest.read("shouldArrangeAllBookingsIfAllRightSource.json")
                .object

        def response = service.calculateBooking(request)

        assertThat(jsonResponce.write(response)).isEqualToJson('shouldArrangeAllBookingsIfAllRightExpected.json')
    }

    @Test
    void requestWhichNotPlacedInOfficeHoursWouldBeIgnored() throws Exception {
        def request = jsonRequest.read("requestWhichNotPlacedInOfficeHoursWouldBeIgnoredSource.json")
                .object

        def response = service.calculateBooking(request)

        assertThat(jsonResponce.write(response)).isEqualToJson('requestWhichNotPlacedInOfficeHoursWouldBeIgnoredExpected.json')
    }

    @Test
    void ifRequestIsOverlapedLatestsWouldBeIgnored() throws Exception {
        def request = jsonRequest.read("ifRequestIsOverlapedLatestsWouldBeIgnoredSource.json")
                .object

        def response = service.calculateBooking(request)

        assertThat(jsonResponce.write(response)).isEqualToJson('ifRequestIsOverlapedLatestsWouldBeIgnoredExpected.json')
    }
}