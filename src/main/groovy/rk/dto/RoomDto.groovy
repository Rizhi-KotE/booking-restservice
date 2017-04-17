package rk.dto

import javax.persistence.*
import java.time.LocalTime

class RoomDto {

    long id

    LocalTime officeHoursBegin

    LocalTime officeHoursEnd
}
