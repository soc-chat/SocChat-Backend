package io.dodn.springboot.storage.db.core.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import java.time.LocalDateTime

@Entity
class ChatRoomEntity(
    @Column
    val name: String,

    @Column
    val image: String,

    @Column
    val description: String,

    @Column
    val expireTime: LocalDateTime = LocalDateTime.now().plusMinutes(30),

) : BaseEntity()
