package io.dodn.springboot.core.api.config

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.dodn.springboot.core.api.dto.MessageDto
import io.dodn.springboot.core.api.dto.MessageType
import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.WebSocketMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler

@Component
class WebSocketHandler : TextWebSocketHandler() {
    private val objectMapper = jacksonObjectMapper()
    override fun afterConnectionEstablished(session: WebSocketSession) {
    }

    override fun handleMessage(
        session: WebSocketSession,
        message: WebSocketMessage<*>,
    ) {
        val receiveMessage: MessageDto = objectMapper.readValue(message.payload.toString(), MessageDto::class.java)

        when (receiveMessage.type) {
            MessageType.JOIN -> {
            }

            MessageType.EXIT -> {}

            MessageType.MESSAGE -> {
            }

            MessageType.SYSTEM -> {}

            MessageType.PING -> {
            }
        }
    }

    override fun handleTransportError(session: WebSocketSession, exception: Throwable) {
    }

    override fun afterConnectionClosed(session: WebSocketSession, closeStatus: CloseStatus) {
    }
}
