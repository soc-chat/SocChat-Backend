package io.dodn.springboot.storage.db.core.repository

import io.dodn.springboot.storage.db.core.entity.ChatRoomEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface ChatRoomRepository : JpaRepository<ChatRoomEntity, Long> {
    fun findByStartTimeAfterAndExpireTimeBefore(
        startTime: LocalDateTime,
        expireTime: LocalDateTime,
    ): List<ChatRoomEntity>
}
