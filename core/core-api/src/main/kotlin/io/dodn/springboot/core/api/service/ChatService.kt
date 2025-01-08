package io.dodn.springboot.core.api.service

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

    fun publishMessage(message: MessageDto) {
//        chatRoomRepository.findById(message.channel).orElseThrow()
        // 메시지 비속어 + 도배 확인
        redisTemplate.convertAndSend("chat", message.toString())
        // 배치 처리를 이용한 메시지 저장
    }
}
