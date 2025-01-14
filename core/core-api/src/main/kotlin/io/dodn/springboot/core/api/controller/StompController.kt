package io.dodn.springboot.core.api.controller

import io.dodn.springboot.core.api.dto.MessageDto
import io.dodn.springboot.core.api.service.ChatService
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller

@Controller
class StompController(
    private val chatService: ChatService,
) {
    @MessageMapping("/send")
    private fun sendMessage(message: MessageDto) {
        chatService.publishMessage(message)
    }
}
