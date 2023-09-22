package ru.agniaendie.hackbackend.models

import jakarta.persistence.Entity
import jakarta.persistence.Id
import lombok.Getter
import lombok.Setter

@Getter
@Setter
@Entity
data class Customer(@Id var code:String, var name:String, var email:String)
