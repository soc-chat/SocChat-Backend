package io.dodn.springboot.core.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.dodn.springboot.core.config.RedisConfig
import io.dodn.springboot.core.dto.MessageDto
import io.dodn.springboot.storage.db.core.entity.ChatEntity
import io.dodn.springboot.storage.db.core.entity.ChatRoomEntity
import io.dodn.springboot.storage.db.core.repository.BulkRepository
import io.dodn.springboot.storage.db.core.repository.ChatRepository
import io.dodn.springboot.storage.db.core.repository.ChatRoomRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ChatService(
    private val chatRoomRepository: ChatRoomRepository,
    private val bulkRepository: BulkRepository,
    private val chatRepository: ChatRepository,
    redisConfig: RedisConfig,
) {
    private val redisTemplate = redisConfig.redisTemplate()
    private val objectMapper = jacksonObjectMapper()
    private val logger = LoggerFactory.getLogger(javaClass)

    private val entities: MutableList<ChatEntity> = mutableListOf()

    fun addEntity(
        channel: Long,
        content: String,
        userId: String,
        type: String,
    ) {
        val entity = ChatEntity(
            channel = channel,
            content = content,
            userId = userId,
            type = type,
        )
        synchronized(this) {
            entities.add(entity)
            if (entities.size >= 100) {
                try {
                    bulkRepository.batchInsert(entities)
                    entities.clear()
                } catch (e: Exception) {
                    logger.error("${e}\n채팅 내역 배치 작업에 실패했어요.")
                }
            }
        }
    }

    fun publishMessage(message: MessageDto, sessionId: String) {
        val result = message.copy(userId = sessionId)
        val messageJson = objectMapper.writeValueAsString(result)
        redisTemplate.convertAndSend("chat", messageJson)
        addEntity(
            channel = result.channel,
            content = result.content,
            userId = result.userId,
            type = result.type.toString(),
        )
    }

    fun getLastChatting(roomId: Long): List<ChatEntity> = chatRepository.findAllByChannel(roomId)

    fun findChatRoom(roomId: Long): ChatRoomEntity = chatRoomRepository.findById(roomId).orElseThrow()

    fun availableChatRoomList(): List<ChatRoomEntity> {
        val currentTime = LocalDateTime.now()
        return chatRoomRepository.findByStartTimeBeforeAndExpireTimeAfter(
            startTime = currentTime,
            expireTime = currentTime,
        )
    }
}
