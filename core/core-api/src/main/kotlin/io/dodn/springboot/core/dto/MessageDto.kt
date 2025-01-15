package io.dodn.springboot.core.dto

data class MessageDto(
    val channel: Long,
    val content: String,
    val isReply: Boolean,
    val userId: Long,
    val parentMessageId: Long?,
    val type: MessageType,
)
