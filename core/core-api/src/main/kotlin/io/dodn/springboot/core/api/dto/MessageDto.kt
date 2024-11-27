package io.dodn.springboot.core.api.dto

data class MessageDto(
    val channel: Long,
    val content: String,
    val userId: Long,
    val type: MessageType,
)
