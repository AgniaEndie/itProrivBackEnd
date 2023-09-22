package ru.agniaendie.hackbackend.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import lombok.Getter
import lombok.Setter

@Entity
@Getter
@Setter
@Table(name = "materials")
data class Material(@Id @Column(name="code")var code : String, var name:String)
