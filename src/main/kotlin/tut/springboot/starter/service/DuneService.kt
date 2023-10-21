package tut.springboot.starter.service

import org.springframework.stereotype.Service
import tut.springboot.starter.datasource.DuneDataSource
import tut.springboot.starter.model.Bank

@Service
class DuneService(private val dataSource: DuneDataSource) {

    fun getQuotesAndCharacter(): Map<String, String> = dataSource.retrieveQuoteCharacterPairs()
    fun getQuote(quote: String): String = dataSource.createQuote(quote)
    fun addQuote(quote: String): String = dataSource.createQuote(quote)


}