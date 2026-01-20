package com.ks.culinario.network.security

import com.ks.culinario.domain.model.User
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtTokenProvider {

    @Value("\${app.jwt.secret}")
    private lateinit var jwtSecret: String

    @Value("\${app.jwt.expiration-in-ms}")
    private lateinit var jwtExpirationInMs: String

    private val secretKey: SecretKey by lazy {
        Keys.hmacShaKeyFor(jwtSecret.toByteArray())
    }

     fun generateToken(user: User): String {
        val now = Date()
        val expiryDate = Date(now.time + jwtExpirationInMs.toLong())

        return Jwts.builder()
            .subject(user.username)
            .issuedAt(Date())
            .expiration(expiryDate)
            .signWith(secretKey)
            .compact()
    }


    fun getUsernameFromJWT(token: String): String {
        val claims: Claims = Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(token)
            .payload

        return claims.subject
    }

    fun validateToken(authToken: String): Boolean {
        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(authToken)
            return true
        } catch (ex: Exception){
            println(ex.message)
        }
        return false
    }
}
