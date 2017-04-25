package rk.entity

import com.fasterxml.jackson.annotation.JsonFormat

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.validation.constraints.NotNull
import java.time.LocalDateTime
import java.time.LocalTime

@Entity
@Table(name = 'meetings')
class Meeting {
    @Id
    @GeneratedValue
    long id

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull
    LocalDateTime submitDate

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @NotNull
    LocalDateTime meetingDateBegin

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @NotNull
    LocalDateTime meetingDateEnd

    @ManyToOne
    @NotNull
    User user;

    @ManyToOne
    @NotNull
    Room room

}
