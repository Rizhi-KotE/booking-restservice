package rk.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import rk.entity.Meeting
import rk.service.MeetingService

import static org.springframework.web.bind.annotation.RequestMethod.POST

@RestController
@RequestMapping(value = '/meeting')
class MeetingRestController {

    @Autowired
    MeetingService service;

    @RequestMapping(value = "/maxless")
    Meeting getMaxLess(@RequestBody Meeting dto){
        service.findMaxPrevious(dto)
    }

    @RequestMapping(value = "/minbigger")
    Meeting getMinBigger(@RequestBody Meeting dto){
        service.findMinFollowing(dto)
    }

    @RequestMapping
    List<Meeting> getList(){
        service.findAll()
    }

    @RequestMapping(value = "/{id}")
    Meeting getById(@PathVariable long id){
        service.getById(id)
    }
    @RequestMapping(method = POST)
    Meeting createMeeting(@RequestBody Meeting dto){
        service.create(dto)
    }
}
