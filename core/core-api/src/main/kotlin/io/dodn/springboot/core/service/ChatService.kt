package io.dodn.springboot.core.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.dodn.springboot.core.config.RedisConfig
import io.dodn.springboot.core.dto.MessageDto
import io.dodn.springboot.storage.db.core.entity.ChatEntity
import io.dodn.springboot.storage.db.core.entity.ChatRoomEntity
import io.dodn.springboot.storage.db.core.repository.ChatRepository
import io.dodn.springboot.storage.db.core.repository.ChatRoomRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ChatService(
    private val chatRoomRepository: ChatRoomRepository,
    private val chatRepository: ChatRepository,
    redisConfig: RedisConfig,
) {
    private val redisTemplate = redisConfig.redisTemplate()
    private val objectMapper = jacksonObjectMapper()

    private val entities: MutableList<ChatEntity> = mutableListOf()

    fun addEntity(
        channel: Long,
        content: String,
        isReply: Boolean,
        userId: Long,
        parentMessageId: Long,
        type: String,
    ) {
        val entity = ChatEntity(
            channel = channel,
            content = content,
            isReply = isReply,
            userId = userId,
            parentMessageId = parentMessageId,
            type = type,
        )
        synchronized(this) {
            entities.add(entity)
            println(entities.size)
            if (entities.size >= 100) {
                chatRepository.batchInsert(entities)
            }
        }
    }

    fun publishMessage(message: MessageDto, sessionId: String) {
        val messageJson = objectMapper.writeValueAsString(message.copy())
        redisTemplate.convertAndSend("chat", messageJson)
        addEntity(
            channel = message.channel,
            content = message.content,
            isReply = message.isReply,
            userId = message.userId,
            parentMessageId = message.parentMessageId,
            type = message.type.toString(),
        )
    }

    fun findChatRoom(roomId: Long): ChatRoomEntity = chatRoomRepository.findById(roomId).orElseThrow()

    fun availableChatRoomList(): List<ChatRoomEntity> {
        val currentTime = LocalDateTime.now()
        return chatRoomRepository.findByStartTimeAfterAndExpireTimeBefore(
            startTime = currentTime,
            expireTime = currentTime,
        )
    }
}
