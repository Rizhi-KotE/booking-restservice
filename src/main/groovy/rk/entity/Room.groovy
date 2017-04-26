package rk.entity

import com.fasterxml.jackson.annotation.JsonFormat

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table
import java.time.LocalTime

@Entity
@Table(name = "rooms")
class Room {
    @Id
    @GeneratedValue
    long id

    @Column(name = 'office_hours_begin')
    @JsonFormat(pattern = "HH:mm")
    LocalTime officeHoursBegin
    @Column(name = 'office_hours_end')
    @JsonFormat(pattern = "HH:mm")
    LocalTime officeHoursEnd
}
