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
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import ru.agniaendie.hackbackend.repositories.IUserRepository
import ru.agniaendie.hackbackend.security.JwtFilter
import java.util.*


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
        return http.csrf { csrf -> csrf.disable() }.cors { cors -> cors.configurationSource(corsConfigurationSource())}
            .authorizeHttpRequests { auth ->
                auth.requestMatchers(
                    "auth/login",
                    "main/**"
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

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource? {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        source.registerCorsConfiguration("/**", config.applyPermitDefaultValues())
        //allow Authorization to be exposed
        config.exposedHeaders = Arrays.asList("Authorization")
        return source
    }
}
