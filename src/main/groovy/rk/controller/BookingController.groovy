package rk.controller

import org.springframework.beans.factory.annotation.Autowired
import rk.dto.BookingRequest
import rk.dto.BookingResponse
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody
import rk.service.BookingService

@Controller
class BookingController {

    @Autowired
    BookingService service

    @ResponseBody
    BookingResponse calculateBooking(@RequestBody BookingRequest request) {
        return service.calculateBooking(request)
    }
}
