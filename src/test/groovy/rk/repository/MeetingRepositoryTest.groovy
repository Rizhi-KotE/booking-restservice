package rk.repository

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import rk.entity.Meeting
import rk.entity.Room
import rk.entity.User
import rk.service.MeetingService
import rk.service.MeetingServiceImpl

import java.time.LocalDateTime

import static java.util.Arrays.asList
import static org.junit.Assert.assertEquals

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MeetingServiceImpl.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@EntityScan(basePackages = ['rk.entity'])
class MeetingRepositoryTest {


    @Autowired
    MeetingService service

    @Autowired
    MeetingRepository repository

    @Autowired
    RoomRepository roomRepository

    @Autowired
    UserRepository userRepository
    private user
    private room

    @After
    void tearDown() throws Exception {
        repository.deleteAll()
    }

    @Before
    void setUp() throws Exception {
        user = userRepository.save(new User())
        room = roomRepository.save(new Room())
        def meeting1 = new Meeting()
        meeting1.meetingDateBegin = LocalDateTime.of(1, 1, 1, 0, 0)
        meeting1.meetingDateEnd = meeting1.meetingDateBegin.plusHours(2)
        meeting1.submitDate = LocalDateTime.of(1, 1, 1, 1, 0)
        meeting1.room = this.room
        meeting1.user = this.user

        def meeting2 = new Meeting()
        meeting2.meetingDateBegin = LocalDateTime.of(1, 1, 1, 2, 0)
        meeting2.meetingDateEnd = meeting2.meetingDateBegin.plusHours(2)
        meeting2.submitDate = LocalDateTime.of(1, 1, 1, 1, 0)
        meeting2.room = this.room
        meeting2.user = this.user

        def meeting3 = new Meeting()
        meeting3.meetingDateBegin = LocalDateTime.of(1, 1, 1, 4, 0)
        meeting3.meetingDateEnd = meeting3.meetingDateBegin.plusHours(2)
        meeting3.submitDate = LocalDateTime.of(1, 1, 1, 1, 0)
        meeting3.room = this.room
        meeting3.user = this.user

        def meeting4 = new Meeting()
        meeting4.meetingDateBegin = LocalDateTime.of(1, 1, 1, 6, 0)
        meeting4.meetingDateEnd = meeting4.meetingDateBegin.plusHours(2)
        meeting4.submitDate = LocalDateTime.of(1, 1, 1, 1, 0)
        meeting4.room = this.room
        meeting4.user = this.user

        def meeting5 = new Meeting()
        meeting5.meetingDateBegin = LocalDateTime.of(1, 1, 1, 10, 0)
        meeting5.meetingDateEnd = meeting5.meetingDateBegin.plusHours(2)
        meeting5.submitDate = LocalDateTime.of(1, 1, 1, 1, 0)
        meeting5.room = this.room
        meeting5.user = this.user
        repository.save(asList(meeting1, meeting2, meeting3, meeting4))
    }

    @Test
    void meetingStartEqualStartOfAnother() throws Exception {
        def meeting = new Meeting()
        meeting.meetingDateBegin = LocalDateTime.of(1, 1, 1, 0, 0)
        meeting.meetingDateEnd = meeting.meetingDateBegin.plusHours(2)

        meeting.room = room
        meeting.user = user

        def count = service.findOverlappedMeetings(meeting)

        assertEquals(1, count)
    }

    @Test
    void meetingStartEqualEndOfLast() throws Exception {
        def meeting = new Meeting()
        meeting.meetingDateBegin = LocalDateTime.of(1, 1, 1, 6, 0)
        meeting.meetingDateEnd = meeting.meetingDateBegin.plusHours(2)
        meeting.room = room
        meeting.user = user

        def count = service.findOverlappedMeetings(meeting)

        assertEquals(0, count)
    }

    @Test
    void meetingEndWhenOtherStarts() throws Exception {
        def meeting = new Meeting()
        meeting.meetingDateBegin = LocalDateTime.of(1, 1, 1, 9, 0)
        meeting.meetingDateEnd = meeting.meetingDateBegin.plusHours(1)
        meeting.room = room
        meeting.user = user

        def count = service.findOverlappedMeetings(meeting)

        assertEquals(0, count)
    }

    @Test
    void meetingEndWhenOtherEnds() throws Exception {
        def meeting = new Meeting()
        meeting.meetingDateBegin = LocalDateTime.of(1, 1, 1, 0, 0)
        meeting.meetingDateEnd = meeting.meetingDateBegin.plusHours(2)
        meeting.room = room
        meeting.user = user

        def count = service.findOverlappedMeetings(meeting)

        assertEquals(1, count)
    }

    @Test
    void meetingOverlappedWithTwoMeetings() throws Exception {
        def meeting = new Meeting()
        meeting.meetingDateBegin = LocalDateTime.of(1, 1, 1, 1, 0)
        meeting.meetingDateEnd = meeting.meetingDateBegin.plusHours(2)
        meeting.room = room
        meeting.user = user

        def count = service.findOverlappedMeetings(meeting)

        assertEquals(2, count)
    }

}