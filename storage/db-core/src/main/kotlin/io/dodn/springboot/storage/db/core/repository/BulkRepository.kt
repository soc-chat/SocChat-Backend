package io.dodn.springboot.storage.db.core.repository

import io.dodn.springboot.storage.db.core.entity.ChatEntity
import org.springframework.jdbc.core.BatchPreparedStatementSetter
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.PreparedStatement
import java.sql.SQLException

@Repository
class BulkRepository(
    private val jdbcTemplate: JdbcTemplate,
) {
    fun batchInsert(entities: MutableList<ChatEntity>) {
        val sql: String =
            "INSERT INTO chat_backup(channel, content, user_id, type, created_at, updated_at) " +
                "VALUES (?, ?, ?, ?, now(), now())"
        jdbcTemplate.batchUpdate(
            sql,
            object : BatchPreparedStatementSetter {
                @Throws(SQLException::class)
                override fun setValues(ps: PreparedStatement, i: Int) {
                    ps.setLong(1, entities[i].channel)
                    ps.setString(2, entities[i].content)
                    ps.setString(3, entities[i].userId)
                    ps.setString(4, entities[i].type)
                }

                override fun getBatchSize(): Int = 100
            },
        )
    }
}
