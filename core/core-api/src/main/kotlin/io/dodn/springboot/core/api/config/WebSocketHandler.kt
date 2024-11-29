package io.dodn.springboot.core.api.config

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.dodn.springboot.core.api.dto.MessageDto
import io.dodn.springboot.core.api.dto.MessageType
import io.dodn.springboot.storage.db.core.KafkaProducer
import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler

@Component
class WebSocketHandler : TextWebSocketHandler() {
    private val producer: KafkaProducer = KafkaProducer()
    private val objectMapper = jacksonObjectMapper()

    override fun afterConnectionEstablished(session: WebSocketSession) {
        sessionList.add(session) // 레디스로 구현 예정
        for (i in chatList) { // 여기선 채팅 리스트를 불러와서 새로운 세션에 보내줌
            session.sendMessage(TextMessage(i.payload.toString()))
        }
    }

    override fun handleMessage(
        session: WebSocketSession,
        message: WebSocketMessage<*>,
    ) {
        val receiveMessage: MessageDto = objectMapper.readValue(message.payload.toString(), MessageDto::class.java)
        producer.sendMessageToKafka("chat-room-${receiveMessage.channel}", receiveMessage.toString())
    }

    override fun handleTransportError(session: WebSocketSession, exception: Throwable) {
        session.sendMessage(TextMessage(MessageDto(0, "에러가 발생했습니다", 0, MessageType.SYSTEM).toString()))
    }

    override fun afterConnectionClosed(session: WebSocketSession, closeStatus: CloseStatus) {
        sessionList.remove(session) // 레디스로 구현 예정
    }

    companion object {
        val sessionList: MutableList<WebSocketSession> = mutableListOf()
        val chatList: MutableList<WebSocketMessage<*>> = mutableListOf()
    }
}
