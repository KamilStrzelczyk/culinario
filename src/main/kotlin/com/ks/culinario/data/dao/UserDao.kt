package com.ks.culinario.data.dao

import com.ks.culinario.data.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UserDao : JpaRepository<UserEntity, Long> {
    fun findByUsername(username: String): Optional<UserEntity>
}
