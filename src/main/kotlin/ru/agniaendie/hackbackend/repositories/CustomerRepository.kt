package ru.agniaendie.hackbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository
import ru.agniaendie.hackbackend.models.Customer

interface CustomerRepository : JpaRepository<Customer, String> {
}
