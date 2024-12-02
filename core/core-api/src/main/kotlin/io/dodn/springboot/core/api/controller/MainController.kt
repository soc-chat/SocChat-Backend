package io.dodn.springboot.core.api.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MainController {
    @GetMapping
    fun happyHacking(): ResponseEntity<Set<String>> = ResponseEntity.ok(setOf("happy", "hacking"))
}
