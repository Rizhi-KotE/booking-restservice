package rk.service

import groovy.transform.PackageScope
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import rk.dto.MeetingRestParams
import rk.entity.Meeting
import rk.exception.BookingException
import rk.repository.MeetingRepository
import rk.repository.RoomRepository
import rk.repository.UserRepository

import javax.validation.Valid
import java.time.LocalTime

import static rk.service.MeetingPredicateBuilder.buildPredicate
import static rk.service.MeetingPredicateBuilder.countOfOverlappedMeetings

@Service
class MeetingServiceImpl implements MeetingService {
    @Autowired
    MeetingRepository repository

    @Autowired
    UserRepository userRepository

    @Autowired
    RoomRepository roomRepository


    @Override
    List<Meeting> getList() {
        repository.findAll()
    }

    @Override
    Meeting getById(long id) {
        repository.findOne(id)
    }

    @Override
    Meeting create(Meeting meeting) {
        def room = roomRepository.findOne(meeting.room.id)
        if (room == null) {
            throw new BookingException("no_room")
        }
        if (!excludeFallOutsideRequests(meeting, room.officeHoursBegin, room.officeHoursEnd)) {

            throw new BookingException("fall_outside_office_hours")
        }
        if (!excludeOverlappedRequests(meeting)) {
            throw new BookingException("overlapped_with_meeting")
        }
        repository.save(meeting)
    }

    @Override
    Page<Meeting> findPageableFiltered(@Valid MeetingRestParams options) {
        def sort = options.sortColumn == null ? null : new Sort(options.direction, options.sortColumn)
        def page = options.page == null ? new PageRequest(0, 10, sort) :
                new PageRequest(options.page - 1, options.pageSize, sort)
        repository.findAll(buildPredicate(options), page)
    }

    @Override
    long findOverlappedMeetings(Meeting meeting) {
        repository.count(countOfOverlappedMeetings(meeting))
    }
/**
 * Exclude meeting which can't fit in office hours
 *
 * @param meeting - meeting that should be filtered
 * @param beginTime - begin of office hours
 * @param endTime - end of office hours
 * @return - filtered list of meeting
 */
    @PackageScope
    boolean excludeFallOutsideRequests(Meeting meeting,
                                       LocalTime beginTime,
                                       LocalTime endTime) {

        def meetingTime = meeting.meetingDateBegin.toLocalTime()
        if (beginTime > meetingTime) return false
        def meetingEndTime = meeting.meetingDateEnd.toLocalTime()
        if (endTime < meetingEndTime) return false
        true
    }

    /**
     * Exclude overlapped meeting
     *
     * @param meeting
     * @return
     */
    @PackageScope
    boolean excludeOverlappedRequests(Meeting meeting) {

        return findOverlappedMeetings(meeting) == 0
    }
}
