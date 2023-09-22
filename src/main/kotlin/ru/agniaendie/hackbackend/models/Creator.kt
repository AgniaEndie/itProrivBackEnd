package ru.agniaendie.hackbackend.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import lombok.Getter
import lombok.Setter

@Getter
@Setter
@Entity
@Table(name = "creators")
data class Creator(@Id @Column(name="code")var code:String, var brand:String)
