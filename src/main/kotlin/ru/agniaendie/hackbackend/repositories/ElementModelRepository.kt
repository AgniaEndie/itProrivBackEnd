package ru.agniaendie.hackbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.agniaendie.hackbackend.models.ElementModel
@Repository
interface ElementModelRepository : JpaRepository<ElementModel, String> {
    fun findByNeuro(code : Int) : ElementModel
}
