package io.dodn.springboot.core.api.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.dodn.springboot.core.api.config.RedisConfig
import io.dodn.springboot.core.api.dto.MessageDto
import io.dodn.springboot.storage.db.core.repository.ChatRoomRepository
import org.springframework.stereotype.Service

@Service
class ChatService(
    private val chatRoomRepository: ChatRoomRepository,
    redisConfig: RedisConfig,
) {
    private val redisTemplate = redisConfig.redisTemplate()
    private val objectMapper = jacksonObjectMapper()

    fun publishMessage(message: MessageDto) {
        // chatRoomRepository.findById(message.channel).orElseThrow() => 방 존재 여부 확인
        // 메시지 비속어 + 도배 확인 => 추가 해야함
        val messageJson = objectMapper.writeValueAsString(message)
        redisTemplate.convertAndSend("chat", messageJson)
        // 배치 처리를 이용한 메시지 저장 => n00개당 한번
    }
}
