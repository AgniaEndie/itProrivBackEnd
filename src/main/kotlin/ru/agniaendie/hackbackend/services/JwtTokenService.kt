package ru.agniaendie.hackbackend.services

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import lombok.NonNull
import org.apache.tomcat.util.codec.binary.Base64
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import ru.agniaendie.hackbackend.models.User
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import java.util.function.Function

@Service
class JwtTokenService {
    @Value("\${foxworld.jwt.secret}")
    private val secret: String = Base64.encodeBase64String("senkosansenkosansenkosansenkosansenkosan".toByteArray())
    fun validateToken(token: String): Boolean {
        return !isTokenExpired(token)
    }

    private fun isTokenExpired(token: String): Boolean {
        return extractExpiration(token)!!.before(Date())
    }

    private fun extractExpiration(token: String): Date? {
        return extractClaim(token) { obj: Claims? -> obj!!.expiration }
    }

    fun generateToken(user: User): String? {
        val now = Date()
        val exp: Date = Date.from(
            LocalDateTime.now().plusMinutes(30).atZone(ZoneId.systemDefault()).toInstant()
        )
        return Jwts.builder().setExpiration(exp).setIssuedAt(now).setSubject(user.getUsername())
            .signWith(io.jsonwebtoken.SignatureAlgorithm.HS256, secret).compact()
    }

    fun revokeToken(@NonNull token: String?) {}
    fun updateToken(@NonNull refreshToken: String?): String? {
        return ""
    }

    fun <T> extractClaim(token: String, claimsTFunction: Function<Claims?, T>): T {
        val claims = extractAllClaims(token)
        return claimsTFunction.apply(claims)
    }

    private fun extractAllClaims(token: String): Claims {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).body
    }
}
