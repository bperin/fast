package com.fast.api

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import com.fast.api.security.BcryptUtil

@SpringBootTest
class FastApplicationTests {

	@Test
	fun contextLoads() {
	}

	@Test
	fun checkBcryptPasswordTrue(){
		val password = "fastworstorbest"

		val encryptedPassword = BcryptUtil.hashPassword(password) as String

	}

}
