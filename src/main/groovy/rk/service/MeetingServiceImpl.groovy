package rk.service

import groovy.transform.PackageScope
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import rk.entity.Meeting
import rk.exception.BookingException
import rk.repository.MeetingRepository
import rk.repository.RoomRepository
import rk.repository.UserRepository

import java.time.LocalTime

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
        if (excludeFallOutsideRequests(meeting, room.officeHoursBegin, room.officeHoursEnd)) {
            throw new BookingException()
        }
        if (excludeOverlappedRequests(meeting)) {
            throw new BookingException()
        }
        repository.save(meeting)
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

        def meetingTime = meeting.meetingDate.toLocalTime()
        if (beginTime > meetingTime) false
        def meetingEndTime = meeting.meetingDate.plusHours(meeting.duration).toLocalTime()
        if (endTime < meetingEndTime) false
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

        def higherKey = repository.findMaxPrevious(meeting.submitDate)
        if (higherKey != null) {
            def endOfMeeting = meeting.meetingDate.plusHours(meeting.duration)
            if (higherKey < endOfMeeting) false
        }
        //Need duration of previous meeting
        def lowerEntry = repository.findMinFollowing(meeting.submitDate)
        if (lowerEntry != null) {
            def endOfLowerEntry = lowerEntry.submitDate.plusHours(lowerEntry.duration)
            if (meeting.meetingDate < endOfLowerEntry) false
        }
        true
    }
}
