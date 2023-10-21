package tut.springboot.starter.datasource.mock

import com.github.javafaker.Dune
import com.github.javafaker.Dune.Quote
import com.github.javafaker.Faker
import org.springframework.stereotype.Repository
import tut.springboot.starter.datasource.DuneDataSource
import java.lang.IllegalArgumentException

@Repository
class MockDuneDataSource : DuneDataSource {

    private var quotes = listOf<String>()
    private var chars = listOf<String>()
    private var quotesAndCharacters = mapOf<String, String>()
    override fun retrieveQuote(character: String): String {
        val charQuote = validateCharacter(character = character)
        return Faker.instance().dune().quote(charQuote)
    }

    private fun validateCharacter(character: String): Quote = try {
        Dune.Quote.valueOf(character.lowercase())
    } catch (e: Exception) {
        throw IllegalArgumentException("Quote with character $character not found")
    }

    override fun retrieveQuotes(): Collection<String> = quotes.getOrGenerate { Faker().dune().quote() }

    private fun Collection<String>.getOrGenerate(generator: () -> String): Collection<String> =
        if (shouldGenerate(this)) {
            generate(generator)
        } else {
            this
        }

    private fun shouldGenerate(list: Collection<String>): Boolean = list.isEmpty()
    private fun shouldGenerateMap(quotes: Map<String, String>): Boolean = quotes.isEmpty()

    private fun generate(generator: () -> String): MutableList<String> {
        val quotesCopy = mutableListOf<String>()
        for (i in 1..100) {
            quotesCopy.add(generator())
        }
        return quotesCopy
    }

    private fun generateMap(): Map<String, String> {
        return retrieveCharacters().zip(retrieveQuotes())
            .toMap()
    }

    override fun retrieveQuoteCharacterPairs(): Map<String, String> {
        if (shouldGenerateMap(quotesAndCharacters)) {
            quotesAndCharacters = generateMap()
        }
        return quotesAndCharacters
    }

    override fun retrieveCharacters(): Collection<String> {
        return chars.getOrGenerate { Faker().dune().character() }
    }

    fun retrieveCharacter(): String {
        return Faker().dune().character()
    }

    override fun createQuote(string: String): String {
        if (checkIfQuoteExists(string, quotes)) throw IllegalArgumentException("Quote $string already exists")
        quotes.addToLast(string)
        return string
    }

    private fun Collection<String>.addToLast(quote: String): List<String> {
        return this.toMutableList().apply {
            add(quote)
        }
    }

    private fun checkIfQuoteExists(quote: String, quotesCollection: Collection<String>): Boolean {
        return quotesCollection.contains(quote)
    }
}