package tut.springboot.starter

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import tut.springboot.starter.model.Bank
import tut.springboot.starter.service.DuneService
import java.lang.IllegalArgumentException

@RestController
@RequestMapping("/dune")
class DuneController(
    private val service: DuneService
) {

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e: NoSuchElementException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.NOT_FOUND)

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleBadRequest(e: IllegalArgumentException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.BAD_REQUEST)

    @GetMapping("/quotes")
    fun getDuneQuotesAndCharacters(): Map<String, String> = service.getQuotesAndCharacter()

    @GetMapping("quotes/{quote}")
    fun getQuote(@PathVariable quote: String): String = service.getQuote(quote)

    @PostMapping("/quotes")
    @ResponseStatus(HttpStatus.CREATED)
    fun addBank(@RequestBody quote: String): String = service.addQuote(quote = quote)

}