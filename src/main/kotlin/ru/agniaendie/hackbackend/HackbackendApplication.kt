package ru.agniaendie.hackbackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HackbackendApplication

fun main(args: Array<String>) {
    runApplication<HackbackendApplication>(*args)
}
