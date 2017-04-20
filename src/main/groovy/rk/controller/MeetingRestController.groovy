package rk.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
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

    @Autowired
    Validator validator;

    @RequestMapping(value = "/maxless")
    Meeting getMaxLess(@RequestBody Meeting dto) {
        service.findMaxPrevious(dto)
    }

    @RequestMapping(value = "/minbigger")
    Meeting getMinBigger(@RequestBody Meeting dto) {
        service.findMinFollowing(dto)
    }

    /**
     * Returns the list of meetings to which the transformations described in MeetingRestParams doc
     * @param params
     * @return
     */
    @RequestMapping
    List<Meeting> getList(@RequestParam Map<String, String> params) {
        service.findAll(mapper.convertValue(params, MeetingRestParams.class))
    }

    @RequestMapping(value = "/{id}")
    Meeting getById(@PathVariable long id) {
        service.getById(id)
    }

    @RequestMapping(method = POST)
    Meeting createMeeting(@RequestBody Meeting dto) {
        service.create(dto)
    }
}
