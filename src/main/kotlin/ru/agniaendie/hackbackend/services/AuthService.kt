package ru.agniaendie.hackbackend.services

import org.springframework.security.crypto.password.PasswordEncoder
import ru.agniaendie.hackbackend.models.ReqAuthModel
import ru.agniaendie.hackbackend.models.ReqRegModel
import ru.agniaendie.hackbackend.models.Role
import ru.agniaendie.hackbackend.models.User
import ru.agniaendie.hackbackend.models.responses.ResAuthModel
import ru.agniaendie.hackbackend.models.responses.ResAuthReg
import ru.agniaendie.hackbackend.repositories.IUserRepository
import java.lang.Exception
import java.util.*

class AuthService {
    private val tokenService = JwtTokenService()
    fun reg(model: ReqRegModel, passwordEncoder: PasswordEncoder,repository: IUserRepository) : ResAuthReg{
        val user = User(UUID.randomUUID().toString(),model.login,passwordEncoder.encode(model.password), Role.USER)
        return try{
            repository.save(user)
            ResAuthReg("Регистрация прошла успешно")
        }catch (e:Exception){
            ResAuthReg("Такой пользователь уже существует")
        }

    }
    fun auth(model: ReqAuthModel, passwordEncoder: PasswordEncoder,repository: IUserRepository) : ResAuthModel{
        return try {
            val user = repository.findByUsername(model.login)
            if(passwordEncoder.matches(model.password, user.password)){
                val token = tokenService.generateToken(user)
                ResAuthModel(user.username,token.toString(),null.toString())
            }else{
                ResAuthModel("","","Неверный логин или пароль")
            }
        }catch (e:Exception){
            ResAuthModel("","","Пользователя с таким логином не существует")
        }
    }
}
