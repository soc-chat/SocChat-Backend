package io.dodn.springboot.core

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.dodn.springboot.core.dto.MessageDto
import org.springframework.data.redis.connection.Message
import org.springframework.data.redis.connection.MessageListener
import org.springframework.http.HttpStatus
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.stereotype.Component
import org.springframework.web.server.ResponseStatusException

@Component
class RedisListener(
    private val messageTemplate: SimpMessageSendingOperations,
) : MessageListener {
    private val objectMapper = jacksonObjectMapper()

    override fun onMessage(message: Message, pattern: ByteArray?) {
        try {
            val result = objectMapper.readValue(message.body, MessageDto::class.java)
            messageTemplate.convertAndSend("/sub/chat/room/${result.channel}", result)
        } catch (error: Exception) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "요청을 처리할 수 없습니다.", error)
        }
    }
}
