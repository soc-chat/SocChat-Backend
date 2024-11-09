package io.dodn.springboot.core.api.config

import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.WebSocketMessage
import org.springframework.web.socket.WebSocketSession

@Component
class WebSocketHandler: WebSocketHandler{
    val sessionList: MutableList<WebSocketSession> = mutableListOf()
    val chatList: MutableList<WebSocketMessage<*>> = mutableListOf()

    override fun afterConnectionEstablished(session: WebSocketSession) {
        sessionList.add(session)
        println("연결 완료 ${session.id}")
        for (i in chatList) {
            session.sendMessage(TextMessage(i.payload.toString()))
        }

    }

    override fun handleMessage(session: WebSocketSession, message: WebSocketMessage<*>) {
        chatList.add(message)
        println(message.toString())
    }

    override fun handleTransportError(session: WebSocketSession, exception: Throwable) {
        println("전송 중 에러")
    }

    override fun afterConnectionClosed(session: WebSocketSession, closeStatus: CloseStatus) {
        println("연결 종료 ${session.id}")
        sessionList.remove(session)
    }

    override fun supportsPartialMessages(): Boolean {
        return false
    }
}