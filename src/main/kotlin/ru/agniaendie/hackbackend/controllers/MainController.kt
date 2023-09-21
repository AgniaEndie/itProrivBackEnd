package ru.agniaendie.hackbackend.controllers

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("main")
@CrossOrigin
class MainController {
    @GetMapping("test")
    @CrossOrigin
    suspend fun test():String{
        return "test"
    }
}
