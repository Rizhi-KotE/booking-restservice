package rk.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import rk.entity.Meeting;
import rk.entity.Room;
import rk.entity.User;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

@Service
public class MeetingDaoImpl implements MeetingDao {

    public static final String SELECT_MAX_LESS = "SELECT * FROM meetings_view WHERE submit_date IN (SELECT MAX(submit_date) FROM meetings_view  WHERE user_id=? OR room_id=? AND meeting_start <= ?);";
    public static final String SELECT_MIN_BIGGER = "SELECT * FROM meetings_view WHERE submit_date IN (SELECT MAX(submit_date) FROM meetings_view  WHERE user_id=? OR room_id=? AND meeting_start <= ?);";
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    RowMapper<Meeting> mapper = (resultSet, i) -> {
        Meeting meet = new Meeting();
        meet.setId(resultSet.getLong("duration"));
        meet.setDuration(resultSet.getInt("duration"));
        Timestamp meeting_start = resultSet.getTimestamp("meeting_start");


        meet.setMeetingDate(LocalDateTime.ofInstant(Instant.ofEpochMilli(meeting_start.getTime()), ZoneId.systemDefault()));
        Timestamp submit_date = resultSet.getTimestamp("submit_date");
        meet.setSubmitDate(LocalDateTime.ofInstant(Instant.ofEpochMilli(submit_date.getTime()), ZoneId.systemDefault()));
        User user = new User();
        user.setId(resultSet.getLong("user_id"));
        user.setUsername(resultSet.getString("username"));
        meet.setUser(user);
        Room room = new Room();
        room.setId(resultSet.getLong("room_id"));
        Time office_hours_begin = resultSet.getTime("office_hours_begin");

        room.setOfficeHoursBegin(office_hours_begin.toLocalTime());
        Time office_hours_end = resultSet.getTime("office_hours_end");
        room.setOfficeHoursEnd(office_hours_end.toLocalTime());
        meet.setRoom(room);
        return meet;
    };

    @Override
    public Meeting findMaxPrevious(Meeting meeting) {
        Object[] params = {meeting.getUser().getId(), meeting.getRoom().getId(), meeting.getMeetingDate()};
        return jdbcTemplate.queryForObject(SELECT_MAX_LESS, params, mapper);
    }

    @Override
    public Meeting findMinFollowing(Meeting meeting) {
        Object[] params = {meeting.getUser().getId(), meeting.getRoom().getId(), meeting.getMeetingDate()};
        return jdbcTemplate.queryForObject(SELECT_MIN_BIGGER, params, mapper);
    }
}
