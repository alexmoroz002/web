package ru.webitmo.soundstats.application

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.webitmo.soundstats.authorization.entities.User

@Repository
interface UsersRepository : JpaRepository<User, String> {
}