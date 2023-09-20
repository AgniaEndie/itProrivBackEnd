package ru.agniaendie.hackbackend.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("main")
class MainController {
    @GetMapping("test")
    suspend fun test():String{
        return "test"
    }
}
