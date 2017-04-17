package rk.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import rk.entity.Room
import rk.repository.RoomRepository

@RestController
@RequestMapping(value = "/room")
class RoomRestController {
    @Autowired
    RoomRepository repository;

    @RequestMapping
    List<Room> getRooms(){
        repository.findAll()
    }

    @RequestMapping(value = "/{id}")
    Room getById(@PathVariable long id){
        repository.findOne(id)
    }
}
