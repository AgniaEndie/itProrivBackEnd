package ru.agniaendie.hackbackend.models

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import lombok.Getter
import lombok.Setter

@Entity
@Getter
@Setter
@Table(name = "elements")
data class ElementModel(@Id var code: String, var weight: Int, var height:Int,@OneToOne var material:Material,@OneToOne var creator: Creator, var title: String, var image:String, var about: String, var neuro: Int)
