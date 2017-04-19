package rk.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.PagingAndSortingRepository
import rk.entity.Meeting

interface MeetingRepository extends JpaRepository<Meeting, Long>, JpaSpecificationExecutor<Meeting>, PagingAndSortingRepository<Meeting, Long> {

////    @Query(value  = "SELECT m FROM Meeting m WHERE m.submitDate IN (SELECT MAX(m2.submitDate) FROM Meeting m2 WHERE m2.user.id=:meeting.user.id or m2.user.room.id=:meeting.user.id AND m2.submitDate <= :meeting.submitDate)")
    Meeting findMaxPrevious(Meeting meeting)
//
////    @Query(value = "SELECT m FROM Meeting m WHERE m.submitDate IN (SELECT MAX(submitDate) FROM Meeting m2 WHERE m2.user.id=:meeting.user.id or m2.user.room.id=:meeting.user.id AND m2.submitDate >= :meeting.submitDate)")
    Meeting findMinFollowing(Meeting meeting)
}