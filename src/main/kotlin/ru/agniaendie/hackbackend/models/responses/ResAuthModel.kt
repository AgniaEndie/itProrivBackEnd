package ru.agniaendie.hackbackend.models.responses

import lombok.Getter
import lombok.Setter

@Getter
@Setter
data class ResAuthModel(val login: String, val token: String, val message: String)
