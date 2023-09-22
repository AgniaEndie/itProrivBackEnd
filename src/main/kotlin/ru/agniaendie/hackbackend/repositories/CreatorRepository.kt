package ru.agniaendie.hackbackend.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.agniaendie.hackbackend.models.Creator
@Repository
interface CreatorRepository : JpaRepository<Creator, String> {
    fun findByCode(code:String) : Creator
}
