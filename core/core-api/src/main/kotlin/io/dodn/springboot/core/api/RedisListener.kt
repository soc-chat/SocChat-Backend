package io.dodn.springboot.core.api

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.dodn.springboot.core.api.config.RedisConfig
import io.dodn.springboot.core.api.dto.MessageDto
import org.springframework.data.redis.connection.Message
import org.springframework.data.redis.connection.MessageListener
import org.springframework.http.HttpStatus
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.web.server.ResponseStatusException

class RedisListener : MessageListener {
    private val redisTemplate = RedisConfig().redisTemplate()
    private val objectMapper = jacksonObjectMapper()
    private lateinit var messageTemplate: SimpMessageSendingOperations

    override fun onMessage(message: Message, pattern: ByteArray?) {
        try {
            val publishMessage: String = redisTemplate.stringSerializer.deserialize(message.body).toString()
            val result = objectMapper.readValue(publishMessage, MessageDto::class.java)
            messageTemplate.convertAndSend("/sub/chat/room/${message.channel}", result)
        } catch (error: Exception) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "요청을 처리할 수 없습니다.")
        }
    }
}
