package ru.webitmo.soundstats

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
class SoundstatsApplicationTests {
	@Autowired
	lateinit var service : UsersService

	@Test
	fun contextLoads() {
		var user1 = User(
			"42abb0odou3wd23wazdipl7xg",
			"BR",
			false,
			"token",
			"refreshToken",
			"Alex Moroz",
			"avatar",
			"premium"
		)
		var user2 = User(
			"42abb0odou3wd23wazdipl7xg",
			"BR",
			false,
			"token2",
			"refreshToken2",
			"Alex Moroz",
			"avatar",
			"premium"
		)
		var user3 = User(
			"42abb0odou3wd23wazdipl7xa",
			"BR",
			false,
			"token1",
			"refreshToken1",
			"Alex Moroz",
			"avatar",
			"premium"
		)
		var user1_ = service.saveUser(user1)
		var user2_ = service.saveUser(user2)
		var user3_ = service.saveUser(user3)
		user3_.accessToken = "nekot"
		assert(service.getUser("42abb0odou3wd23wazdipl7xq") == null)
		assert(service.getUser("42abb0odou3wd23wazdipl7xa") != null)
	}

}
