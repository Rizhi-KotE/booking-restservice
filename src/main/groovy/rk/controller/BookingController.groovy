package rk.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import rk.dto.BookingRequest
import rk.dto.Calendar
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody
import rk.service.BookingService
import rk.service.BookingServiceImpl

import javax.validation.Valid

@Controller
class BookingController {

    @Autowired
    BookingService service

    @ResponseBody
    @RequestMapping(value = "/calculateBooking")
    Calendar calculateBooking(@RequestBody @Valid BookingRequest request) {
        return service.calculateBooking(request)
    }
}
