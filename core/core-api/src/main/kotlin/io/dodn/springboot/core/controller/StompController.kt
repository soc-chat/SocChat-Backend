package io.dodn.springboot.core.controller

import io.dodn.springboot.core.dto.MessageDto
import io.dodn.springboot.core.service.BucketService
import io.dodn.springboot.core.service.ChatService
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpMessageHeaderAccessor
import org.springframework.stereotype.Controller

@Controller
class StompController(
    private val chatService: ChatService,
    private val bucketService: BucketService,
) {
    @MessageMapping("/send")
    private fun sendMessage(message: MessageDto, test: SimpMessageHeaderAccessor) {
        val sessionId = test.sessionAttributes?.get("sessionId").toString()
        val bucket = bucketService.resolveBucket(sessionId)
        if (bucket.tryConsume(1)) chatService.publishMessage(message, sessionId)
    }
}
