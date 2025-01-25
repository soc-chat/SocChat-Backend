package io.dodn.springboot.core.dto

import io.dodn.springboot.storage.db.core.entity.ChatEntity

data class MessageDto(
    val id: Long,
    val channel: Long,
    val content: String,
    val userId: String,
    val type: MessageType,
) {
    companion object {
        fun from(entity: ChatEntity): MessageDto = MessageDto(
            id = entity.id,
            channel = entity.channel,
            content = entity.content,
            userId = entity.userId,
            type = MessageType.valueOf(entity.type),
        )
    }
}
