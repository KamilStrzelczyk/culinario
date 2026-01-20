package com.ks.culinario.data.dao

import com.ks.culinario.data.entity.UserEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager

@DataJpaTest
class UserDaoTest {

    @Autowired
    private lateinit var entityManager: TestEntityManager

    @Autowired
    private lateinit var userDao: UserDao

    @Test
    fun `should find user by username when user exists`() {
        // GIVEN
        val user = UserEntity(username = "testuser", email = "test@test.com", password = "password")
        entityManager.persistAndFlush(user)

        // WHEN
        val foundUser = userDao.findByUsername("testuser")

        // THEN
        assertThat(foundUser).isPresent
        assertThat(foundUser.get().username).isEqualTo(user.username)
    }

    @Test
    fun `should return empty when user does not exist`() {
        // GIVEN
        // No user is saved

        // WHEN
        val foundUser = userDao.findByUsername("nonexistent")

        // THEN
        assertThat(foundUser).isNotPresent
    }

    @Test
    fun `should save and retrieve user`() {
        // GIVEN
        val user = UserEntity(username = "newuser", email = "new@test.com", password = "password")

        // WHEN
        userDao.save(user)
        val foundUser = userDao.findById(user.id!!)

        // THEN
        assertThat(foundUser).isPresent
        assertThat(foundUser.get().username).isEqualTo("newuser")
    }
}
