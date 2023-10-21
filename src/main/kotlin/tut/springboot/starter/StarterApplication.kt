package tut.springboot.starter

import com.github.javafaker.Faker
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.KeyValue
import org.apache.kafka.streams.kstream.Grouped
import org.apache.kafka.streams.kstream.KStream
import org.apache.kafka.streams.kstream.Materialized
import org.apache.kafka.streams.kstream.Produced
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.kafka.annotation.EnableKafkaStreams
import org.springframework.messaging.Message
import org.springframework.messaging.support.MessageBuilder
import java.util.function.Consumer
import java.util.function.Function
import java.util.function.Supplier

@SpringBootApplication
class StarterApplication {

    @Bean
    fun produceChuckNorris(): Supplier<Message<String>> {
        return Supplier {
            MessageBuilder.withPayload(Faker.instance().chuckNorris().fact()).build()
        }
    }

    @Bean
    fun consumeChuckNorris(): Consumer<Message<String>> {
        return Consumer {
            println("FACT: ${it.payload}")
        }
    }

    @Bean
    fun processWords(): Function<KStream<String?, String>, KStream<String, Long>> {
        return Function { input ->
            input
                .flatMapValues { value: String -> value.lowercase().split("\\W+".toRegex()) }
                .map { key, value -> KeyValue(value, value) }
                .groupByKey(Grouped.with(Serdes.String(), Serdes.String()))
                .count(Materialized.`as`("word-count-state-store"))
                .toStream()
                .apply {
                    to("counts", Produced.with(Serdes.String(), Serdes.Long()))
                }
        }
    }

//    @Bean
//    fun kStream(streamsBuilder: StreamsBuilder): KStream<String, String> {
//        val messagesStream: KStream<String, String> =
//            streamsBuilder.stream(EXAMPLE_TOPIC_NAME, Consumed.with(Serdes.String(), Serdes.String()))
//
//        messagesStream
//            .flatMapValues { value -> value.split(" ") }
//            .filter { _, value -> value.contains("[0-9]".toRegex()) }
//
//        messagesStream.to("aggregated_messages", Produced.with(Serdes.String(), Serdes.String()))
//
//        return messagesStream
//    }

}

fun main(args: Array<String>) {
    runApplication<StarterApplication>(*args)

}


