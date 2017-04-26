package rk.dto

import org.springframework.data.domain.Page

class BookingPageable<T> implements Page<T> {

    @Delegate
    Page<T> page

    @Override
    int getNumber() {
        page.number + 1
    }
}
