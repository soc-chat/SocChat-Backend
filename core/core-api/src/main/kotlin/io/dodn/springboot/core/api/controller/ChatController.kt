package io.dodn.springboot.core.api.controller

import io.dodn.springboot.core.api.dto.ChatRoomDto
import io.dodn.springboot.core.api.service.ChatService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class ChatController(private val chatService: ChatService) {
    @GetMapping("/room")
    fun findAllRooms(): ResponseEntity<List<ChatRoomDto>> =
        ResponseEntity.ok(chatService.availableChatRoomList().map { ChatRoomDto.from(it) })

    @GetMapping("/room/{roomId}")
    fun getLastChatting(@PathVariable roomId: Long): ResponseEntity<ChatRoomDto> =
        ResponseEntity.ok(ChatRoomDto.from(chatService.findChatRoom(roomId)))
}
