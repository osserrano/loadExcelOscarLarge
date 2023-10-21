package tut.springboot.starter.kafka

import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.Message
import org.springframework.stereotype.Component
import java.util.function.Consumer

@Component
class DuneConsumer {
    private val logger = LoggerFactory.getLogger(javaClass)

    @KafkaListener(topics = ["dune-quotes"], groupId = "kotlin-dune-consumer", autoStartup = "true")
    fun consumeDuneQuotes(): Consumer<Message<String>> {
        return Consumer {
            println("FACT: ${it.payload}")
        }
    }

}