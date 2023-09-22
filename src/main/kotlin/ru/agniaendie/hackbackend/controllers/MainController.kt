package ru.agniaendie.hackbackend.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import ru.agniaendie.hackbackend.models.responses.ResUpload
import ru.agniaendie.hackbackend.repositories.CreatorRepository
import ru.agniaendie.hackbackend.repositories.ElementModelRepository
import ru.agniaendie.hackbackend.repositories.MaterialRepository
import ru.agniaendie.hackbackend.services.MainService

@RestController
@RequestMapping("main")
@CrossOrigin
class MainController {
    val service = MainService()

    @Autowired
    lateinit var elemRepository: ElementModelRepository
    @Autowired
    lateinit var creatorRepository: CreatorRepository
    @Autowired
    lateinit var materialRepository: MaterialRepository

    @GetMapping("test")
    @CrossOrigin
    suspend fun test(): String {
        return "test"
    }

    @PostMapping("upload")
    @CrossOrigin
    suspend fun uploadImage(@RequestParam("file") file: MultipartFile): ResUpload {
        return service.uploadImage(file, elemRepository, materialRepository,creatorRepository)
    }
}
