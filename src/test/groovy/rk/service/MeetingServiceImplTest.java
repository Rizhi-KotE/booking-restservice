package rk.service;

import org.junit.Before;
import org.junit.Test;
import rk.entity.Meeting;
import rk.entity.Room;
import rk.exception.BookingException;
import rk.repository.MeetingRepository;
import rk.repository.RoomRepository;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;


public class MeetingServiceImplTest {

    private MeetingRepository repository;
    private MeetingServiceImpl meetingService;
    private RoomRepository roomRepository;

    @Before
    public void setUp() throws Exception {
        repository = mock(MeetingRepository.class);
        roomRepository = mock(RoomRepository.class);
        meetingService = new MeetingServiceImpl();
        meetingService.setRepository(repository);
        meetingService.setRoomRepository(roomRepository);
    }

    @Test(expected = BookingException.class)
    public void testMeetingFallOutOfficeHoursBefore() throws Exception {
        Meeting meeting = new Meeting();
        Room room = new Room();
        meeting.setRoom(room);
        meeting.setMeetingDateBegin(LocalDateTime.of(0, 1, 1, 2, 0));
        meeting.setMeetingDateEnd(meeting.getMeetingDateBegin().plusHours(2));
        room.setOfficeHoursBegin(LocalTime.of(3, 0));
        room.setOfficeHoursEnd(LocalTime.of(23, 0));

        when(meetingService.findOverlappedMeetings(any(Meeting.class)))
                .thenReturn(0L);

        when(roomRepository.findOne(any(Long.class)))
                .thenReturn(room);

        meetingService.create(meeting);

        verify(repository, times(0)).save(meeting);
    }

    @Test(expected = BookingException.class)
    public void testWhenHasOverlappedMeeting() throws Exception {
        Meeting meeting = new Meeting();
        Room room = new Room();
        meeting.setRoom(room);
        meeting.setMeetingDateBegin(LocalDateTime.of(0, 1, 1, 2, 0));
        meeting.setMeetingDateEnd(LocalDateTime.of(0, 1, 1, 2, 0));
        room.setOfficeHoursBegin(LocalTime.of(1, 0));
        room.setOfficeHoursEnd(LocalTime.of(23, 0));

        when(meetingService.findOverlappedMeetings(any(Meeting.class)))
                .thenReturn(1L);

        when(roomRepository.findOne(any(Long.class)))
                .thenReturn(room);

        meetingService.create(meeting);

        verify(repository, times(0)).save(meeting);
    }



}