package rk.exception

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
