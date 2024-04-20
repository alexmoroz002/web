package ru.webitmo.soundstats.users

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.webitmo.soundstats.users.entities.User

@Repository
interface UsersRepository : JpaRepository<User, String> {
}