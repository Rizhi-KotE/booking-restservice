package rk.service

import rk.dto.MeetingRestParams
import rk.entity.Meeting

import javax.validation.Valid

interface MeetingService {
    List<Meeting> getList()

    Meeting getById(long id)

    Meeting create(Meeting meetingDto)

    Meeting findMaxPrevious(Meeting meeting)

    Meeting findMinFollowing(Meeting meeting)

    List<Meeting> findAll(MeetingRestParams params)
}