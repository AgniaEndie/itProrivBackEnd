package ru.agniaendie.hackbackend.models.responses

import lombok.Getter
import lombok.Setter

@Getter
@Setter
data class ResUpload(var code: String, var weight: Int, var height:Int, var material:String, var creator: String, var title: String, var image:String, var about: String)

