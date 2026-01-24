package com.ks.culinario.network.security

import com.ks.culinario.application.service.CustomUserDetailsService
import com.ks.culinario.domain.repository.TokenRepository
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.security.SignatureException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.time.Instant

@Component
class JwtAuthenticationFilter(
    private val tokenProvider: JwtTokenProvider,
    private val userDetailsService: CustomUserDetailsService,
    private val tokenRepository: TokenRepository
) : OncePerRequestFilter() {

    companion object {
        private const val BEARER_PREFIX = "Bearer "
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val jwt = getJwtFromRequest(request)

            if (jwt != null && tokenProvider.validateToken(jwt)) {
                
                val token = tokenRepository.findByToken(jwt)
                val isTokenValid = token != null && !token.revoked && token.expiryDate.isAfter(Instant.now())

                if (isTokenValid) {
                    val username = tokenProvider.getUsernameFromJWT(jwt)
                    val userDetails = userDetailsService.loadUserByUsername(username)
                    
                    val authentication = UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.authorities
                    )
                    authentication.details = WebAuthenticationDetailsSource().buildDetails(request)

                    SecurityContextHolder.getContext().authentication = authentication
                }
            }
        } catch (ex: SignatureException) {
            logger.error("Invalid JWT signature: ", ex)
        } catch (ex: MalformedJwtException) {
            logger.error("Invalid JWT token: ", ex)
        } catch (ex: ExpiredJwtException) {
            logger.error("Expired JWT token: ", ex)
        } catch (ex: UnsupportedJwtException) {
            logger.error("Unsupported JWT token: ", ex)
        } catch (ex: IllegalArgumentException) {
            logger.error("JWT claims string is empty: ", ex)
        }

        filterChain.doFilter(request, response)
    }

    private fun getJwtFromRequest(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")
        if (bearerToken != null && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(BEARER_PREFIX.length)
        }
        return null
    }
}
