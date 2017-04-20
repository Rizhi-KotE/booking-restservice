package rk.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

class BookingException extends Exception{
    BookingException() {
    }

    BookingException(String var1) {
        super(var1)
    }

    BookingException(String var1, Throwable var2) {
        super(var1, var2)
    }

    BookingException(Throwable var1) {
        super(var1)
    }

    BookingException(String var1, Throwable var2, boolean var3, boolean var4) {
        super(var1, var2, var3, var4)
    }
}
