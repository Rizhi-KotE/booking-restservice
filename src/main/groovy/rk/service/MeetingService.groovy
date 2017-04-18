package rk.service

import org.springframework.data.repository.query.Param
import rk.dto.MeetingDto
import rk.entity.Meeting

interface MeetingService {
    List<Meeting> getList()

    Meeting getById(long id)

    Meeting create(Meeting meetingDto)

    Meeting findMaxPrevious(Meeting meeting)

    Meeting findMinFollowing(Meeting meeting)
}