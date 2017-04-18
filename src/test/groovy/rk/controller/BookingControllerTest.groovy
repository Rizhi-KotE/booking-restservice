package rk.controller

import org.junit.Test
import org.junit.experimental.results.ResultMatchers
import org.junit.runner.RunWith
import org.skyscreamer.jsonassert.JSONAssert
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultMatcher
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import rk.BookingServiceApplication
import rk.service.BookingServiceImpl

import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith(SpringRunner.class)
@WebMvcTest(BookingController.class)
@ContextConfiguration(classes = [BookingServiceApplication.class,BookingServiceImpl.class])
class BookingControllerTest {

    @Autowired
    MockMvc mvc;

    @Test
    void testRestMethod() throws Exception {
        def request = post("/calculateBooking")
                .contentType(APPLICATION_JSON)
                .content("{\n" +
                "  \"officeHoursBegin\": \"0900\",\n" +
                "  \"officeHoursEnd\": \"1730\",\n" +
                "  \"requests\": [\n" +
                "    {\n" +
                "      \"submitDate\": \"2011-03-17 10:17:06\",\n" +
                "      \"meetingStartTime\": \"2011-03-21 09:00\",\n" +
                "      \"employerId\": \"EMP1\",\n" +
                "      \"meetingDuration\": 2\n" +
                "    }\n" +
                "  ]\n" +
                "}")
        def string = mvc.perform(request).andReturn().response.getContentAsString()
        def expected = "{\n" +
                "  \"calendar\": {\n" +
                "    \"2011-03-21\": [\n" +
                "      {\n" +
                "        \"begin\": \"09:00\",\n" +
                "        \"end\": \"11:00\",\n" +
                "        \"employerId\": \"EMP1\"\n" +
                "      }]\n" +
                "  }\n" +
                "}"
        JSONAssert.assertEquals(expected, string, false)
    }

    @Test
    void checkBeginEndValidation() throws Exception {
        def request = post("/calculateBooking")
                .contentType(APPLICATION_JSON)
                .content("{\n" +
                "  \"officeHoursBegin\": \"1700\",\n" +
                "  \"officeHoursEnd\": \"0900\",\n" +
                "  \"requests\": []\n" +
                "}")
        mvc.perform(request).andExpect(status().is4xxClientError())
    }

    @Test
    void checkNaturalDuration() throws Exception {
        def request = post("/calculateBooking")
                .contentType(APPLICATION_JSON)
                .content("{\n" +
                "  \"officeHoursBegin\": \"0900\",\n" +
                "  \"officeHoursEnd\": \"1730\",\n" +
                "  \"requests\": [\n" +
                "    {\n" +
                "      \"submitDate\": \"2011-03-17 10:17:06\",\n" +
                "      \"meetingStartTime\": \"2011-03-21 09:00\",\n" +
                "      \"employerId\": \"EMP1\",\n" +
                "      \"meetingDuration\": 0\n" +
                "    }\n" +
                "  ]\n" +
                "}")
        mvc.perform(request).andExpect(status().is4xxClientError())
    }

    @Test
    void meetingSartTimeShouldBeAfterSubmitTime() throws Exception {
        def request = post("/calculateBooking")
                .contentType(APPLICATION_JSON)
                .content("{\n" +
                "  \"officeHoursBegin\": \"0900\",\n" +
                "  \"officeHoursEnd\": \"1730\",\n" +
                "  \"requests\": [\n" +
                "    {\n" +
                "      \"submitDate\": \"2011-03-17 10:17:06\",\n" +
                "      \"meetingStartTime\": \"2011-03-16 09:00\",\n" +
                "      \"employerId\": \"EMP1\",\n" +
                "      \"meetingDuration\": 1\n" +
                "    }\n" +
                "  ]\n" +
                "}")
        mvc.perform(request).andExpect(status().is4xxClientError())
    }
}