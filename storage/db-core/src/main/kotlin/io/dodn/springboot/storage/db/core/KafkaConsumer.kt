package io.dodn.springboot.storage.db.core

import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.apache.kafka.shaded.com.google.protobuf.Duration
import java.util.Properties

class KafkaConsumer {
    companion object {
        private val configs = Properties().apply {
            put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9094")
            put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java.name)
            put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java.name)
        }
        private val consumer: KafkaConsumer<String, String> = KafkaConsumer(configs)
    }

    fun receiveMessage(roomList: List<String>) {
        consumer.subscribe(roomList)
        while (true) {
            val records: ConsumerRecords<String, String> = consumer.poll(java.time.Duration.ofMillis(1000))
            records.forEach { record ->
                println(record)
            }
        }
    }
}
