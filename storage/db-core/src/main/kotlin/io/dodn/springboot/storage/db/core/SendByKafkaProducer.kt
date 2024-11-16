package io.dodn.springboot.storage.db.core

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.clients.producer.RecordMetadata
import org.apache.kafka.common.serialization.StringSerializer
import java.util.Properties
import java.util.concurrent.Future

class SendByKafkaProducer {
    companion object {
        private val configs = Properties().apply {
            put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9094")
            put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java.name)
            put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java.name)
        }
        private val producer: KafkaProducer<String, String> = KafkaProducer(configs)
        fun sendMessageToKafka(topic: String, message: String): Future<RecordMetadata> =
            producer.send(ProducerRecord(topic, message))
    }
}
