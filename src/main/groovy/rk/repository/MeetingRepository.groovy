package rk.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.PagingAndSortingRepository
import rk.entity.Meeting

interface MeetingRepository extends JpaRepository<Meeting, Long>, JpaSpecificationExecutor<Meeting>, PagingAndSortingRepository<Meeting, Long> {

    Meeting closestPreviousMeeting(Meeting meeting)

    Meeting closestFollowingMeeting(Meeting meeting)
}