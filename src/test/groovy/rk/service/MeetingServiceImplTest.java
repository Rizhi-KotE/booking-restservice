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

//    private MeetingRepository repository;
//    private MeetingServiceImpl meetingService;
//    private RoomRepository roomRepository;
//
//    @Before
//    public void setUp() throws Exception {
//        repository = mock(MeetingRepository.class);
//        roomRepository = mock(RoomRepository.class);
//        meetingService = new MeetingServiceImpl();
//        meetingService.setRepository(repository);
//        meetingService.setRoomRepository(roomRepository);
//    }
//
//    @Test(expected = BookingException.class)
//    public void testMeetingFallOutOfficeHoursBefore() throws Exception {
//        Meeting meeting = new Meeting();
//        Room room = new Room();
//        meeting.setRoom(room);
//        meeting.setDuration(2);
//        room.setOfficeHoursBegin(LocalTime.of(3,0));
//        room.setOfficeHoursEnd(LocalTime.of(23,0));
//        meeting.setMeetingDate(LocalDateTime.of(0,1,1,2,0));
//
//        when(roomRepository.findOne(any(Long.class)))
//                .thenReturn(room);
//
//        meetingService.create(meeting);
//
//        verify(repository, times(1)).save(meeting);
//    }
//
//    @Test
//    public void testWhenNoMoreMeetings() throws Exception {
//        Meeting meeting = new Meeting();
//        Room room = new Room();
//        meeting.setRoom(room);
//        meeting.setDuration(2);
//        room.setOfficeHoursBegin(LocalTime.of(1,0));
//        room.setOfficeHoursEnd(LocalTime.of(23,0));
//        meeting.setMeetingDate(LocalDateTime.of(0,1,1,2,0));
//
//        when(repository.findMaxPrevious(any(Meeting.class)))
//                .thenReturn(null);
//        when(repository.findMinFollowing(any(Meeting.class)))
//                .thenReturn(null);
//
//        when(roomRepository.findOne(any(Long.class)))
//                .thenReturn(room);
//
//        meetingService.create(meeting);
//
//        verify(repository, times(1)).save(meeting);
//    }
//
//    @Test(expected = BookingException.class)
//    public void dontSaveWhenHasPreviosOverlap() throws Exception {
//        Meeting meeting = new Meeting();
//        Room room = new Room();
//        meeting.setRoom(room);
//        meeting.setDuration(2);
//        room.setOfficeHoursBegin(LocalTime.of(1,0));
//        room.setOfficeHoursEnd(LocalTime.of(23,0));
//        meeting.setMeetingDate(LocalDateTime.of(0,1,1,2,0));
//
//        Meeting previous = new Meeting();
//        previous.setMeetingDate(LocalDateTime.of(0,1,1,1,0));
//        previous.setDuration(2);
//        Meeting following = new Meeting();
//        following.setMeetingDate(LocalDateTime.of(0, 1, 1, 23, 0));
//        following.setDuration(2);
//
//        when(repository.findMaxPrevious(any(Meeting.class)))
//                .thenReturn(previous);
//        when(repository.findMinFollowing(any(Meeting.class)))
//                .thenReturn(following);
//
//        when(roomRepository.findOne(any(Long.class)))
//                .thenReturn(room);
//
//        meetingService.setRepository(repository);
//
//        meetingService.create(meeting);
//    }
//
//    @Test(expected = BookingException.class)
//    public void dontSaveWhenHasFollowingOverlap() throws Exception {
//        Meeting meeting = new Meeting();
//        Room room = new Room();
//        meeting.setRoom(room);
//        meeting.setDuration(2);
//        room.setOfficeHoursBegin(LocalTime.of(1,0));
//        room.setOfficeHoursEnd(LocalTime.of(23,0));
//        meeting.setMeetingDate(LocalDateTime.of(0,1,1,2,0));
//
//        Meeting previous = new Meeting();
//        previous.setMeetingDate(LocalDateTime.of(0,1,1,1,0));
//        previous.setDuration(0);
//        Meeting following = new Meeting();
//        following.setMeetingDate(LocalDateTime.of(0, 1, 1, 3, 0));
//        following.setDuration(2);
//
//        when(repository.findMaxPrevious(any(Meeting.class)))
//                .thenReturn(previous);
//        when(repository.findMinFollowing(any(Meeting.class)))
//                .thenReturn(following);
//
//        when(roomRepository.findOne(any(Long.class)))
//                .thenReturn(room);
//
//        meetingService.setRepository(repository);
//
//        meetingService.create(meeting);
//    }
//
//    @Test
//    public void saveWhenWhenHasNoOverlap() throws Exception {
//        Room room = new Room();
//        room.setOfficeHoursBegin(LocalTime.of(1,0));
//        room.setOfficeHoursEnd(LocalTime.of(23,0));
//
//        Meeting meeting = new Meeting();
//        meeting.setRoom(room);
//        meeting.setDuration(2);
//        meeting.setMeetingDate(LocalDateTime.of(0,1,1,9,0));
//
//
//        Meeting previous = new Meeting();
//        previous.setMeetingDate(LocalDateTime.of(0,1,1,1,0));
//        previous.setDuration(8);
//        Meeting following = new Meeting();
//        following.setMeetingDate(LocalDateTime.of(0, 1, 1, 11, 0));
//        following.setDuration(2);
//
//        when(repository.findMaxPrevious(any(Meeting.class)))
//                .thenReturn(previous);
//        when(repository.findMinFollowing(any(Meeting.class)))
//                .thenReturn(following);
//
//        when(roomRepository.findOne(any(Long.class)))
//                .thenReturn(room);
//
//        meetingService.setRepository(repository);
//
//        meetingService.create(meeting);
//
//        verify(repository, times(1))
//                .save(any(Meeting.class));
//    }
//
//    @Test(expected = BookingException.class)
//    public void notSaveWhenHasEqualsMeetingTime() throws Exception {
//        Room room = new Room();
//        room.setOfficeHoursBegin(LocalTime.of(1,0));
//        room.setOfficeHoursEnd(LocalTime.of(23,0));
//
//        Meeting meeting = new Meeting();
//        meeting.setRoom(room);
//        meeting.setDuration(2);
//        meeting.setMeetingDate(LocalDateTime.of(0,1,1,9,0));
//
//
//        Meeting previous = new Meeting();
//        previous.setMeetingDate(LocalDateTime.of(0,1,1,9,0));
//        previous.setDuration(8);
//        Meeting following = new Meeting();
//        following.setMeetingDate(LocalDateTime.of(0, 1, 1, 11, 0));
//        following.setDuration(2);
//
//        when(repository.findMaxPrevious(any(Meeting.class)))
//                .thenReturn(previous);
//        when(repository.findMinFollowing(any(Meeting.class)))
//                .thenReturn(following);
//
//        when(roomRepository.findOne(any(Long.class)))
//                .thenReturn(room);
//
//        meetingService.setRepository(repository);
//
//        meetingService.create(meeting);
//
//    }
//
//    @Test(expected = BookingException.class)
//    public void notSaveWhenHasEqualsFollowingMeetingTime() throws Exception {
//        Room room = new Room();
//        room.setOfficeHoursBegin(LocalTime.of(1,0));
//        room.setOfficeHoursEnd(LocalTime.of(23,0));
//
//        Meeting meeting = new Meeting();
//        meeting.setRoom(room);
//        meeting.setDuration(2);
//        meeting.setMeetingDate(LocalDateTime.of(0,1,1,1,0));
//
//
//        Meeting previous = new Meeting();
//        previous.setMeetingDate(LocalDateTime.of(0,1,1,9,0));
//        previous.setDuration(8);
//        Meeting following = new Meeting();
//        following.setMeetingDate(LocalDateTime.of(0, 1, 1, 9, 0));
//        following.setDuration(2);
//
//        when(repository.findMaxPrevious(any(Meeting.class)))
//                .thenReturn(previous);
//        when(repository.findMinFollowing(any(Meeting.class)))
//                .thenReturn(following);
//
//        when(roomRepository.findOne(any(Long.class)))
//                .thenReturn(room);
//
//        meetingService.setRepository(repository);
//
//        meetingService.create(meeting);
//
//    }
}