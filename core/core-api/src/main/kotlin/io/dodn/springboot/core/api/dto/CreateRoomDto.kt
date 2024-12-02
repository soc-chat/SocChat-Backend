package io.dodn.springboot.core.api.dto

data class CreateRoomDto(
    val name: String,
    val description: String,
    val roomBannerImage: String,
    val tags: String,
)
