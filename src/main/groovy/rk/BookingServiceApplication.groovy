package rk

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import rk.service.BookingService

@SpringBootApplication
class BookingServiceApplication {

    @Bean
    BookingService bookingService(){
        new BookingService()
    }

    static void main(String[] args) {
        SpringApplication.run(BookingServiceApplication.class, args)
    }
}
