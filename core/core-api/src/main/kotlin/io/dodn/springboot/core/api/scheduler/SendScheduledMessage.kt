package io.dodn.springboot.core.api.scheduler

import io.dodn.springboot.core.api.config.WebSocketHandler.Companion.sessionList
import io.dodn.springboot.core.api.dto.MessageDto
import io.dodn.springboot.core.api.dto.MessageType
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.web.socket.TextMessage

@Component
class SendScheduledMessage {
    @Scheduled(fixedDelay = 5000)
    fun sendMessageByScheduled() {
        sessionList.forEach { i ->
            i.sendMessage(
                TextMessage(
                    MessageDto(
                        content = "10초마다 핑",
                        type = MessageType.PING,
                        userId = 0,
                        channel = 0,
                    ).toString(),
                ),
            )
        }
    }
}
