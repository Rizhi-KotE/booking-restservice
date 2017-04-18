package rk.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import rk.entity.Meeting

import java.time.LocalDateTime

interface MeetingRepository extends JpaRepository<Meeting, Long> {


}