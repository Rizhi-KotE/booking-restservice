package rk.repository

import org.springframework.data.jpa.repository.JpaRepository
import rk.entity.User

interface UserRepository extends JpaRepository<User, Long> {

}