package ru.agniaendie.hackbackend.configuration

import lombok.RequiredArgsConstructor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.AccessDeniedHandlerImpl
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import ru.agniaendie.hackbackend.repositories.IUserRepository
import ru.agniaendie.hackbackend.security.JwtFilter


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
class SecurityConfig(repos: IUserRepository) {
    val filter: JwtFilter = JwtFilter(repos)
    @Bean
    fun encoder(): PasswordEncoder? {
        return BCryptPasswordEncoder()
    }
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http.csrf { csrf -> csrf.disable() }
            .authorizeHttpRequests { auth ->
                auth.requestMatchers(
                    "auth/**",
                    "main/skins/**",
                    "main/capes/**",
                    "main/assets/**"
                ).permitAll().anyRequest().authenticated()
            }
            .sessionManagement { sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .anonymous { anonimous -> anonimous.disable() }
            .addFilterBefore(filter, UsernamePasswordAuthenticationFilter::class.java)
            .exceptionHandling { exception ->
                exception
                    .authenticationEntryPoint { request, response, authException ->
                        System.err.println("Failed! $authException \n $request \n $response")
                        response.status = 401
                    }
                    .accessDeniedHandler(AccessDeniedHandlerImpl())
            }.securityContext { securityContext -> securityContext.requireExplicitSave(false) }.build()
    }
}
