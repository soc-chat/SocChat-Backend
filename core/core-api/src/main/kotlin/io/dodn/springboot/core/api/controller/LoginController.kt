package io.dodn.springboot.core.api.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class LoginController {
    @GetMapping("/login/oauth2/google")
    fun getLoginLink(
        @RequestParam code: String,
    ): ResponseEntity<String> {
        println(code)
        return ResponseEntity.ok("ok")
    }
}
