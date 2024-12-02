package io.dodn.springboot.core.api.dto

data class MessageDto(
    val messageId: Long,
    val channel: Long,
    val content: String,
    val parentMessageId: Long,
    val userId: Long,
    val type: MessageType,
    val isBlind: Boolean,
)
