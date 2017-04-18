package rk.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import rk.entity.User
import rk.repository.UserRepository

@RestController
@RequestMapping(value = '/user')
class UserRestController {
    @Autowired
    UserRepository repository

    @RequestMapping
    List<User> getList(){
        repository.findAll()
    }

    @RequestMapping(value = "/{id}")
    User getById(@PathVariable long id){
        repository.findOne(id)
    }
}
