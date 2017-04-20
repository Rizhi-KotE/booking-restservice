package rk.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

import rk.dto.MeetingRestParams
import rk.entity.Meeting
import rk.service.MeetingService

import org.springframework.validation.Validator;

import static org.springframework.web.bind.annotation.RequestMethod.POST

@RestController
@RequestMapping(value = '/meeting')
class MeetingRestController {

    @Autowired
    ObjectMapper mapper

    @Autowired
    MeetingService service

    /**
     * Returns the list of meetings to which the transformations described in MeetingRestParams doc
     * @param params
     * @return
     */
    @GetMapping
    List<Meeting> getList(@RequestParam Map<String, String> params) {
        service.findAll(mapper.convertValue(params, MeetingRestParams.class))
    }

    @GetMapping(value = "/{id}")
    Meeting getById(@PathVariable long id) {
        service.getById(id)
    }

    @PostMapping
    Meeting createMeeting(@RequestBody Meeting dto) {
        service.create(dto)
    }
}
