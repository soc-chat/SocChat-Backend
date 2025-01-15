package io.dodn.springboot.core.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.dodn.springboot.core.config.RedisConfig
import io.dodn.springboot.core.dto.MessageDto
import io.dodn.springboot.storage.db.core.entity.ChatRoomEntity
import io.dodn.springboot.storage.db.core.repository.ChatRoomRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ChatService(
    private val chatRoomRepository: ChatRoomRepository,
    redisConfig: RedisConfig,
) {
    private val redisTemplate = redisConfig.redisTemplate()
    private val objectMapper = jacksonObjectMapper()

    fun publishMessage(message: MessageDto) {
        val messageJson = objectMapper.writeValueAsString(message)
        redisTemplate.convertAndSend("chat", messageJson)
    }

    fun findChatRoom(roomId: Long): ChatRoomEntity = chatRoomRepository.findById(roomId).orElseThrow()

    fun availableChatRoomList(): List<ChatRoomEntity> =
        chatRoomRepository.findByExpireTimeBefore(LocalDateTime.now())
}
