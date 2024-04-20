package ru.webitmo.soundstats

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import ru.webitmo.soundstats.users.UsersService

@SpringBootTest
@ActiveProfiles("test")
class SoundstatsApplicationTests {
	@Autowired
	lateinit var service : UsersService

	@Test
	fun contextLoads() {}

}
