package rk.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import rk.entity.Meeting

import java.time.LocalDateTime
import java.time.LocalTime

interface MeetingRepository extends JpaRepository<Meeting, Long>, JpaSpecificationExecutor<Meeting>, PagingAndSortingRepository<Meeting, Long> {

}