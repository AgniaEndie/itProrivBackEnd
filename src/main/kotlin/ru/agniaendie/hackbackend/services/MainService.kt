package ru.agniaendie.hackbackend.services

import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate
import org.springframework.web.multipart.MultipartFile
import ru.agniaendie.hackbackend.models.responses.NeuroResponse
import ru.agniaendie.hackbackend.models.responses.ResUpload
import ru.agniaendie.hackbackend.repositories.CreatorRepository
import ru.agniaendie.hackbackend.repositories.ElementModelRepository
import ru.agniaendie.hackbackend.repositories.MaterialRepository


@Service
class MainService {
    var root = "http://foxworld.online:25601"
    suspend fun uploadImage(file:MultipartFile, repository: ElementModelRepository, materialRepository: MaterialRepository, creatorRepository: CreatorRepository): ResUpload {
        val headers = HttpHeaders()
        headers.contentType = MediaType.MULTIPART_FORM_DATA
        val body: MultiValueMap<String, Any> = LinkedMultiValueMap()
        body.add("file",file.bytes)
        val requestEntity = HttpEntity<MultiValueMap<String, Any>>(body, headers)
        val serverUrl = "$root/"
        val restTemplate = RestTemplate()
        val response = restTemplate.postForEntity(serverUrl, requestEntity, NeuroResponse::class.java)
        val elem = repository.findByNeuro((response.body!!.neuroId))
        var creator = ""
        var material = ""
        val result = ResUpload(elem.code,elem.weight,elem.height,elem.material.name,elem.creator.brand,elem.title,elem.image,elem.about);

        return result
    }



}
