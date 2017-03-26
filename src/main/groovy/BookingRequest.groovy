import com.fasterxml.jackson.annotation.JsonFormat

class BookingRequest {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HHmm")
    Calendar officeHoursBegin
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HHmm")
    Calendar officeHoursEnd

    List<Request> requests

    static class Request {
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        Calendar submitDate
        String employerId
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
        Calendar meetingStartTime
        int meetingDuration
    }
}
