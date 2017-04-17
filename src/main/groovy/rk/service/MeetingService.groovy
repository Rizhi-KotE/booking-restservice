package rk.service

import rk.dto.MeetingDto
import rk.entity.Meeting

interface MeetingService {
    List<Meeting> getList()

    Meeting getById(long id)

    Meeting create(Meeting meetingDto)
}