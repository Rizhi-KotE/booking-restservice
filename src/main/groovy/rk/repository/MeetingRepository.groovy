package rk.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import rk.entity.Meeting

import javax.persistence.NamedQueries
import javax.persistence.NamedQuery
import java.time.LocalDateTime

interface MeetingRepository extends JpaRepository<Meeting, Long>{

    @Query("SELECT m FROM Meeting m WHERE m.submitDate IN (SELECT MAX(submitDate) FROM Meeting) AND submitDate < :submitDate")
    Meeting findMaxPrevious(@Param(value = 'submitDate') LocalDateTime submitDate)

    @Query("SELECT m FROM Meeting m WHERE m.submitDate IN (SELECT MIN(submitDate) FROM Meeting) AND submitDate > :submitDate")
    Meeting findMinFollowing(@Param(value = 'submitDate') LocalDateTime submitDate)

}