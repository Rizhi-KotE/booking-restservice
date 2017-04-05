package rk

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import rk.service.BookingServiceImpl

@SpringBootApplication
class BookingServiceApplication {

    @Bean
    BookingServiceImpl bookingService(){
        new BookingServiceImpl()
    }

    static void main(String[] args) {
        SpringApplication.run(BookingServiceApplication.class, args)
    }
}
