package rk.dao

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import rk.entity.Meeting

interface MeetingDao {

    @Query(value  = "SELECT m FROM Meeting m WHERE m.submitDate IN (SELECT MAX(m2.submitDate) FROM Meeting m2 WHERE m2.user.id=:meeting.user.id or m2.user.room.id=:meeting.user.id AND m2.submitDate <= :meeting.submitDate)")
    Meeting findMaxPrevious(@Param(value = 'meeting') Meeting meeting)

    @Query(value = "SELECT m FROM Meeting m WHERE m.submitDate IN (SELECT MAX(submitDate) FROM Meeting m2 WHERE m2.user.id=:meeting.user.id or m2.user.room.id=:meeting.user.id AND m2.submitDate >= :meeting.submitDate)")
    Meeting findMinFollowing(@Param(value = 'meeting') Meeting meeting)

}