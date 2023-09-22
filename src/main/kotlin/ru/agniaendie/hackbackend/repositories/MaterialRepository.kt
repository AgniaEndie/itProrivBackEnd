package ru.agniaendie.hackbackend.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.agniaendie.hackbackend.models.Material
@Repository
interface MaterialRepository : JpaRepository<Material, String> {
    fun findByCode(code : String) : Material
}
