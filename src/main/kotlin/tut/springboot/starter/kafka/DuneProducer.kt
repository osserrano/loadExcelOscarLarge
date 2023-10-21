package tut.springboot.starter.kafka

import com.github.javafaker.Faker
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import java.lang.Thread.sleep

@Component
class DuneProducer @Autowired constructor(private val kafkaTemplate: KafkaTemplate<String, String>) {
    fun produceDuneQuotes() {
        // send messages repeatedly with a 1000ms delay
        // to the "dune-quotes" topic with a random character and quote
        for (i in 0..1000) {
            kafkaTemplate.send("dune-quotes", Faker.instance().dune().character(), Faker.instance().dune().quote())
            sleep(1000)
        }
    }

}