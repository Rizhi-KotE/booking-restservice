package rk.repository

import org.springframework.data.jpa.repository.JpaRepository
import rk.entity.Room

interface RoomRepository extends JpaRepository<Room, Long>{

}