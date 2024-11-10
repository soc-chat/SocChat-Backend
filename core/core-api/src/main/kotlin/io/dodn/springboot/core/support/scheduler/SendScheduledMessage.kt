package io.dodn.springboot.core.support.scheduler

import io.dodn.springboot.core.api.config.WebSocketHandler.Companion.sessionList
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.web.socket.TextMessage

// 웹소켓 연결이 끊어지는걸 방지, 연결중인 세션에 메시지를 보냅니다.

@Component
class SendScheduledMessage {
    @Scheduled(fixedDelay = 5000)
    fun sendMessageByScheduled() {
        for (i in sessionList) {
            i.sendMessage(TextMessage("10초마다 전체 세션에 보내는중 :)"))
        }
    }
}
