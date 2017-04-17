package rk.repository

import org.springframework.data.jpa.repository.JpaRepository
import rk.entity.Meeting

interface MeetingRepository extends JpaRepository<Meeting, Long>{

}