package rk.entity

import com.fasterxml.jackson.annotation.JsonFormat

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = 'meetings')
class Meeting {
    @Id
    @GeneratedValue
    long id

    @Column(name = 'submit_date')
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime submitDate

    @Column(name = 'meeting_start')
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime meetingDate

    @Column(name = 'duration')
    int duration

    @ManyToOne
    User user;

    @ManyToOne
    Room room

}
