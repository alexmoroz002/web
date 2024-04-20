package ru.webitmo.soundstats.users

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.webitmo.soundstats.users.entities.User

@Service
class UsersService {
    @Autowired
    lateinit var repository : UsersRepository

    fun saveUser(user: User) : User {
        return repository.save(user)
    }

    fun getUser(id : String) : User? {
        val found = repository.findById(id)
        return if (found.isPresent) found.get() else null
    }
}