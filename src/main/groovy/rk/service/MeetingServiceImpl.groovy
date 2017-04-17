package rk.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import rk.dto.MeetingDto
import rk.dto.RoomDto
import rk.entity.Meeting
import rk.entity.Room
import rk.entity.User
import rk.repository.MeetingRepository
import rk.repository.RoomRepository
import rk.repository.UserRepository

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
        repository.save(meeting)
    }
}
