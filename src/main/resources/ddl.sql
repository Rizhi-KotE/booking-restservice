CREATE OR REPLACE VIEW  meetings_view
AS SELECT m.id,m.duration, m.submit_date, m.meeting_start, m.room_id, m.user_id, r.office_hours_begin, r.office_hours_end,
     u.username FROM meetings AS m
  INNER JOIN rooms AS r ON m.room_id=r.id
  INNER JOIN users AS u ON m.user_id=u.id;