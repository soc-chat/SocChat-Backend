package io.dodn.springboot.core.api.scheduler

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class SendScheduledMessage {
    @Scheduled(fixedDelay = 5000)
    fun sendMessageByScheduled() {
    }
}
