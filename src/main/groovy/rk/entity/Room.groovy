package rk.entity

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
    LocalTime officeHoursBegin
    @Column(name = 'office_hours_end')
    LocalTime officeHoursEnd
}
