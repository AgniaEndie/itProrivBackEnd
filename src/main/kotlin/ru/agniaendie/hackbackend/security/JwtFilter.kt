package ru.agniaendie.hackbackend.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import lombok.RequiredArgsConstructor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import ru.agniaendie.hackbackend.models.User
import ru.agniaendie.hackbackend.repositories.IUserRepository
import ru.agniaendie.hackbackend.services.JwtTokenService

@Component
@RequiredArgsConstructor
class JwtFilter constructor(var repository: IUserRepository) : OncePerRequestFilter(){
    val log = LoggerFactory.getLogger(this.javaClass)
    val jwtTokenService: JwtTokenService  = JwtTokenService()
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val header = request.getHeader("Authorization")
        log.error(header)
        if (header != null && header.startsWith("Bearer ")) {
            val token = header.substring(7)
            if (jwtTokenService.validateToken(token)) {
                val username: String = jwtTokenService.extractClaim(token) { claims ->
                    claims!!.get(
                        "sub",
                        String::class.java
                    )
                }
                val user: User = repository.findByUsername(username)
                val authenticationToken = UsernamePasswordAuthenticationToken(
                    user,
                    user.role,
                    user.authorities
                )
                val ctx = SecurityContextHolder.createEmptyContext()
                SecurityContextHolder.setContext(ctx)
                ctx.authentication = authenticationToken
                if (SecurityContextHolder.getContext().authentication == null) {
                    log.error("Произошла ошибка в модуле авторизации")
                }
            } else {
                log.error("Токен истек$token")
            }
        }
        filterChain.doFilter(request, response)
    }
}
