package com.ks.culinario.data

import com.ks.culinario.data.dao.UserDao
import com.ks.culinario.data.entity.UserEntity
import org.springframework.boot.CommandLineRunner
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class DataInitializer(
    private val userDao: UserDao,
    private val passwordEncoder: PasswordEncoder
) : CommandLineRunner {

    override fun run(vararg args: String) {
        if (userDao.count() == 0L) {
            val admin = UserEntity(
                username = "admin",
                email = "test@test.com",
                password = passwordEncoder.encode("admin123")!!
            )
            userDao.save(admin)
            println(">>> BAZA DANYCH ZAINICJOWANA <<<")
        }
    }
}
