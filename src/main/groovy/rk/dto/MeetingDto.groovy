package rk.dto

import com.fasterxml.jackson.annotation.JsonFormat

import javax.persistence.*
import java.time.LocalDateTime

class MeetingDto {

    long id

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime submitDate

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime meetingDate

    double duration

    UserDto user

    RoomDto room

}
