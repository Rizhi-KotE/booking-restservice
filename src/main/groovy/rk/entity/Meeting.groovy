package rk.entity

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
    LocalDateTime submitDate

    @Column(name = 'meeting_start')
    LocalDateTime meetingDate

    @Column(name = 'duration')
    double duration

    @ManyToOne
    User user;

}
