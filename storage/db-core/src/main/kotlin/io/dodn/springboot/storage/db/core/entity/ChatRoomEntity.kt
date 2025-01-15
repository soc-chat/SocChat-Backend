package io.dodn.springboot.storage.db.core.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "chat_room")
class ChatRoomEntity(
    @Column
    val name: String,

    @Column
    val image: String,

    @Column
    val description: String,

    @Column
    val startTime: LocalDateTime = LocalDateTime.now().plusMinutes(3),

    @Column
    val expireTime: LocalDateTime = LocalDateTime.now().plusMinutes(30),

) : BaseEntity()
