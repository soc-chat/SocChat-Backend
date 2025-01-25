package io.dodn.springboot.storage.db.core.repository

import io.dodn.springboot.storage.db.core.entity.ChatEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ChatRepository : JpaRepository<ChatEntity, Long> {
    fun findAllByChannel(channel: Long): List<ChatEntity>
}
