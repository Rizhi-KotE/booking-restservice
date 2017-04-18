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

    Meeting getMax(){
        repository.find()
    }

    @Override
    Meeting create(Meeting meeting) {
        def room = roomRepository.findOne(meeting.room.id)
        if (!excludeFallOutsideRequests(meeting, room.officeHoursBegin, room.officeHoursEnd)) {
            throw new BookingException()
        }
        if (!excludeOverlappedRequests(meeting)) {
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
        if (beginTime > meetingTime) return false
        def meetingEndTime = meeting.meetingDate.plusHours(meeting.duration).toLocalTime()
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

        def following = repository.findMinFollowing(meeting)
        if (following != null) {
            def endOfMeeting = meeting.meetingDate.plusHours(meeting.duration)
            if (following.meetingDate < endOfMeeting) return false
        }
        //Need duration of previous meeting
        def previous = repository.findMaxPrevious(meeting)
        if (previous != null) {
            def endOfLowerEntry = previous.meetingDate.plusHours(previous.duration)
            if (meeting.meetingDate < endOfLowerEntry) return false
        }
        true
    }
}
