package rk.repository

import org.springframework.data.repository.query.Param
import rk.entity.Meeting

interface MeetingCustomRepository {

    Meeting closestPreviousMeeting(Meeting meeting)

    Meeting closestFollowingMeeting(Meeting meeting)
}