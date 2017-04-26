package rk.service

import org.springframework.data.domain.Page
import rk.dto.MeetingRestParams
import rk.entity.Meeting

import javax.validation.Valid

interface MeetingService {
    List<Meeting> getList()

    Meeting getById(long id)

    Meeting create(Meeting meetingDto)

    Page<Meeting> findPageableFiltered(MeetingRestParams params)

    long findOverlappedMeetings(Meeting meeting)
}