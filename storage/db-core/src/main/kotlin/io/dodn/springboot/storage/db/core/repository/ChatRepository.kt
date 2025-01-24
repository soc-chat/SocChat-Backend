package io.dodn.springboot.storage.db.core.repository

import io.dodn.springboot.storage.db.core.entity.ChatEntity
import org.springframework.jdbc.core.BatchPreparedStatementSetter
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.PreparedStatement
import java.sql.SQLException

@Repository
class ChatRepository(
    private val jdbcTemplate: JdbcTemplate,
) {
    fun batchInsert(entities: MutableList<ChatEntity>) {
        val sql: String =
            "INSERT INTO chat_backup(channel, content, is_reply, user_id, parent_message_id, type, created_at, updated_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, now(), now())"
        jdbcTemplate.batchUpdate(
            sql,
            object : BatchPreparedStatementSetter {
                @Throws(SQLException::class)
                override fun setValues(ps: PreparedStatement, i: Int) {
                    ps.setLong(1, entities[i].channel)
                    ps.setString(2, entities[i].content)
                    ps.setBoolean(3, entities[i].isReply)
                    ps.setLong(4, entities[i].userId)
                    ps.setLong(5, entities[i].parentMessageId)
                    ps.setString(6, entities[i].type)
                }

                override fun getBatchSize(): Int = 100
            },
        )
    }
}
