package ru.agniaendie.hackbackend.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.agniaendie.hackbackend.models.User

@Repository
interface IUserRepository : JpaRepository<User, String> {
    fun save (user : User) : User
    fun findByUsername (username : String) : User
}
