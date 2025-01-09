package io.dodn.springboot.storage.db.core.repository

import io.dodn.springboot.storage.db.core.entity.ChatRoomEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface ChatRoomRepository : JpaRepository<ChatRoomEntity, Long> {
    fun findByExpireTimeBefore(expire: LocalDateTime): List<ChatRoomEntity>
}
