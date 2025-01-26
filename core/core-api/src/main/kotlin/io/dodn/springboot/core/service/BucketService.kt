package io.dodn.springboot.core.service

import io.github.bucket4j.Bandwidth
import io.github.bucket4j.Bucket
import io.github.bucket4j.Refill
import org.springframework.stereotype.Service
import java.time.Duration
import java.util.concurrent.ConcurrentHashMap

@Service
class BucketService {
    private val cache: MutableMap<String, Bucket> = ConcurrentHashMap<String, Bucket>()

    fun resolveBucket(sessionId: String): Bucket =
        cache.computeIfAbsent(sessionId) { this.newBucket() }

    private fun newBucket(): Bucket =
        Bucket
            .builder()
            .addLimit(Bandwidth.classic(5, Refill.intervally(1, Duration.ofSeconds(1))))
            .build()
}
