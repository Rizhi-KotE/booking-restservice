package rk

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@EntityScan(
        basePackageClasses = [ BookingServiceApplication.class, Jsr310JpaConverters.class ])
@SpringBootApplication
class BookingServiceApplication {

    static void main(String[] args) {
        SpringApplication.run(BookingServiceApplication.class, args)
    }
}
