class BookingService {

    Comparator<Calendar> byDate = { first, second ->
                def years = first.get(Calendar.YEAR) <=> first.get(Calendar.YEAR)
                if (years == 0) {
                    def month = first.get(Calendar.MONTH) <=> first.get(Calendar.MONTH)
                    if (month == 0) {
                        return first.get(Calendar.DATE) <=> first.get(Calendar.DATE)
                    } else {
                        return month
                    }
                } else {
                    return years
                }
            }

    BookingResponse calcCalendar(BookingRequest request) {
        Map<Calendar, List<BookingResponse.Booking>> map = new TreeMap(byDate)
        request.requests.forEach {

            def bookingListOnDate = map.get(it.meetingStartTime)
            if(bookingListOnDate==null){
                bookingListOnDate = new ArrayList()
                map.put(it.meetingStartTime, bookingListOnDate)
            }
            bookingListOnDate.add(new BookingResponse.Booking(it))
        }
        new BookingResponse(calendar: map)
    }
}
