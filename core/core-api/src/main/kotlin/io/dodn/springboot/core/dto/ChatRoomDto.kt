package io.dodn.springboot.core.dto

import io.dodn.springboot.storage.db.core.entity.ChatRoomEntity
import java.time.LocalDateTime

data class ChatRoomDto(
    val name: String,
    val image: String,
    val description: String,
    val startTime: LocalDateTime,
    val expireTime: LocalDateTime,
) {
    companion object {
        fun from(entity: ChatRoomEntity): ChatRoomDto = ChatRoomDto(
            name = entity.name,
            image = entity.image,
            description = entity.description,
            expireTime = entity.expireTime,
            startTime = entity.startTime,
        )
    }
}
