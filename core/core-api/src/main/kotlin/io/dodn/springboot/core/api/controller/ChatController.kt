package io.dodn.springboot.core.api.controller

import io.dodn.springboot.core.api.dto.CreateRoomDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ChatController {
    @PostMapping("/rooms")
    fun createRoom(@RequestBody body: CreateRoomDto) {
    }

    @GetMapping("/rooms")
    fun findAllRooms() {
    }

    @GetMapping("/chat")
    fun getLastChatting(@RequestParam roomId: Long) {
    }
}
