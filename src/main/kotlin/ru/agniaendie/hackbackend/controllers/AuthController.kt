package ru.agniaendie.hackbackend.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.agniaendie.hackbackend.models.ReqAuthModel
import ru.agniaendie.hackbackend.models.ReqRegModel
import ru.agniaendie.hackbackend.models.responses.ResAuthModel
import ru.agniaendie.hackbackend.models.responses.ResAuthAuth
import ru.agniaendie.hackbackend.repositories.IUserRepository
import ru.agniaendie.hackbackend.services.AuthService

@RestController
@RequestMapping("auth")
@CrossOrigin
class AuthController {
    @Autowired
    lateinit var repository : IUserRepository

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    val authService: AuthService = AuthService()
    @PostMapping("reg")
    suspend fun reg(@RequestBody model: ReqRegModel) : ResAuthAuth {
        return authService.reg(model, passwordEncoder,repository)
    }
    @PostMapping("login")
    suspend fun auth(@RequestBody model: ReqAuthModel) : ResAuthModel {
        return authService.auth(model, passwordEncoder, repository)
    }
}
