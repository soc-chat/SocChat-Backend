package io.dodn.springboot.core.api.config

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.dodn.springboot.core.api.dto.MessageDto
import io.dodn.springboot.core.api.dto.MessageType
import io.dodn.springboot.storage.db.core.KafkaProducer
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.WebSocketMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler

@Component
class WebSocketHandler : TextWebSocketHandler() {
    private val producer: KafkaProducer = KafkaProducer()
    private val objectMapper = jacksonObjectMapper()

    //    private val redisTemplate = RedisConfig().redisTemplate()
    private val logger = LoggerFactory.getLogger(WebSocketHandler::class.java)

    override fun afterConnectionEstablished(session: WebSocketSession) {
    }

    override fun handleMessage(
        session: WebSocketSession,
        message: WebSocketMessage<*>,
    ) {
        val receiveMessage: MessageDto = objectMapper.readValue(message.payload.toString(), MessageDto::class.java)
        when (receiveMessage.type) {
            MessageType.JOIN -> {
                chatRoomList.getOrPut(receiveMessage.channel) {
                    mutableListOf()
                }.add(session)
            }

            MessageType.EXIT -> chatRoomList[receiveMessage.channel]?.toMutableList()?.minus(session)
            MessageType.PING -> chatList[receiveMessage.channel]?.toMutableList()?.add(message)
            MessageType.MESSAGE -> {
                chatList[receiveMessage.channel]?.toMutableList()?.add(message)
                if (chatRoomList[receiveMessage.channel] != null) {
                    chatRoomList[receiveMessage.channel]?.forEach { it.sendMessage(message) }
                }
            }

            MessageType.SYSTEM -> chatList[receiveMessage.channel]?.toMutableList()?.add(message)
            // 구현체 카프카 + 레디스로 전환 + 리팩토링
        }
//        producer.sendMessageToKafka("chat-room-${receiveMessage.channel}", receiveMessage.toString())
    }

    override fun handleTransportError(session: WebSocketSession, exception: Throwable) {
        session.sendMessage(
            TextMessage(
                MessageDto(
                    messageId = 0L,
                    channel = 0L,
                    content = "문제가 발생했어요",
                    parentMessageId = 0L,
                    userId = 0L,
                    type = MessageType.SYSTEM,
                    isBlind = false,
                ).toString(),
            ),
        )
    }

    override fun afterConnectionClosed(session: WebSocketSession, closeStatus: CloseStatus) {
        chatRoomList.values.forEach { list ->
            list.remove(session)
        }
        // 카프카에 메시지로 커넥션 종료시 보내주기
    }

    companion object {
        val sessionList: MutableList<WebSocketSession> = mutableListOf()
        val chatList: MutableMap<Long, MutableList<WebSocketMessage<*>>> = mutableMapOf()
        val chatRoomList: MutableMap<Long, MutableList<WebSocketSession>> = mutableMapOf()
    }
}
