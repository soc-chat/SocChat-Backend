package io.dodn.springboot.core.config

import org.springframework.context.event.EventListener
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.stereotype.Component
import org.springframework.web.socket.messaging.SessionConnectEvent

@Component
class StompEventListener(
    private val messagingTemplate: SimpMessagingTemplate,
) {
    @EventListener
    fun handleWebSocketConnectListener(event: SessionConnectEvent) {
        val accessor = StompHeaderAccessor.wrap(event.message)

        val sessionId = accessor.sessionAttributes?.get("sessionId") ?: return
        println(sessionId)

        messagingTemplate.convertAndSendToUser(accessor.sessionId.toString(), "/queue/session-id", sessionId)
    }
}
