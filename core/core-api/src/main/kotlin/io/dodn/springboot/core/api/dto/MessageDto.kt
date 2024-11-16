package io.dodn.springboot.core.api.dto

data class MessageDto(
    var content: String,
    var userId: Long,
    var type: MessageType,
)
