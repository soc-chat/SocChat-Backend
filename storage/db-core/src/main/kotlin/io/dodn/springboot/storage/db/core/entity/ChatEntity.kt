package io.dodn.springboot.storage.db.core.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "chat_backup")
class ChatEntity(
    @Column
    val channel: Long,

    @Column
    val content: String,

    @Column
    val isReply: Boolean,

    @Column
    val userId: Long,

    @Column
    val parentMessageId: Long,

    @Column
    val type: String,

) : BaseEntity()
