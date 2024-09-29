package com.manager.container.container_manager.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class StartController {

    @GetMapping("")
    fun helloWorld(): String {
        return "Hello"
    }
}