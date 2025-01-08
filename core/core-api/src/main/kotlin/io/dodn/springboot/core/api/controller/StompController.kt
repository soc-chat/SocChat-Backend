package io.dodn.springboot.core.api.controller

import io.dodn.springboot.core.api.dto.MessageDto
import io.dodn.springboot.core.api.service.ChatService
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.stereotype.Controller

@Controller
class StompController(
    private val chatService: ChatService,
    private val simpMessageSendingOperations: SimpMessageSendingOperations,
) {
    @MessageMapping("/send")
    private fun sendMessage(message: MessageDto) {
        chatService.publishMessage(message)
        simpMessageSendingOperations.convertAndSend("/sub/chat/room/${message.channel}", message)
    }
}
