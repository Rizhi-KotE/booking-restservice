package rk.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import rk.dto.BookingPageable
import rk.dto.MeetingRestParams
import rk.entity.Meeting
import rk.exception.BookingException
import rk.service.MeetingService

import javax.validation.Valid

import static org.springframework.web.bind.annotation.RequestMethod.POST

@RestController
@RequestMapping(value = '/meeting')
class MeetingRestController {

    @Autowired
    ObjectMapper mapper

    @Autowired
    MeetingService service

    @ResponseBody
    @ExceptionHandler(BookingException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    String handleException(BookingException e) {
        e.getMessage()
    }

    /**
     * Returns the page of meetings to which applied the transformations described in MeetingRestParams javadoc
     * @param params
     * @return
     */
    @RequestMapping
    Page<Meeting> getList(@RequestParam Map<String, String> params) {
        new BookingPageable<>(
                service.findPageableFiltered(
                        mapper.convertValue(params, MeetingRestParams.class)))
    }

    @RequestMapping(value = "/{id}")
    Meeting getById(@PathVariable long id) {
        service.getById(id)
    }

    @RequestMapping(method = POST)
    Meeting createMeeting(@Valid @RequestBody Meeting dto) {

        service.create(dto)
    }
}
