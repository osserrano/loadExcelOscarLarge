package tut.springboot.starter.datasource

import java.util.Collections

interface DuneDataSource {
    fun retrieveQuotes(): Collection<String>
    fun retrieveQuoteCharacterPairs(): Map<String, String>
    fun retrieveCharacters(): Collection<String>
    fun retrieveQuote(character: String): String
    fun createQuote(string: String): String
}