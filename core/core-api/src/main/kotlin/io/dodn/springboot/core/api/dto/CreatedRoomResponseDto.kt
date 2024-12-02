package io.dodn.springboot.core.api.dto

data class CreatedRoomResponseDto(
    val createBy: Long,
    val id: Long,
    val owner: Long,
)
