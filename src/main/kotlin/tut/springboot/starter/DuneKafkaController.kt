package tut.springboot.starter

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import tut.springboot.starter.kafka.DuneProducer

@RestController
class DuneKafkaController(
    private val producer: DuneProducer
) {
    @PostMapping("/dune/quotes/produce")
    @ResponseStatus(HttpStatus.CREATED)
    fun produceQuotes() = producer.produceDuneQuotes()

}