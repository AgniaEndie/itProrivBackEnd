package ru.agniaendie.hackbackend.services

import org.springframework.core.io.FileSystemResource
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
import java.io.File
import java.io.FileOutputStream
import java.nio.file.Files
import java.nio.file.Paths


@Service
class MainService {
//    var root = "http://foxworld.online:25601"
    var root = "http://46.242.121.246:25601"
    suspend fun uploadImage(file:MultipartFile, repository: ElementModelRepository, materialRepository: MaterialRepository, creatorRepository: CreatorRepository): ResUpload {
        val headers = HttpHeaders()
        headers.contentType = MediaType.MULTIPART_FORM_DATA
        val body: MultiValueMap<String, Any> = LinkedMultiValueMap()

        val re : File = File.createTempFile("KotlinHack", ".tmp")
        val fs = FileOutputStream(re).write(file.bytes)
        val fss = FileSystemResource(re)
        body.add("file",fss)
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

    suspend fun getImage(code: String): ByteArray {
        print(code)
        return Files.readAllBytes(Paths.get("images/$code.jpg"))
    }

}
