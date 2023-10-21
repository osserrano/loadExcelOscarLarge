package tut.springboot.starter.datasource.mock

import org.springframework.stereotype.Repository
import tut.springboot.starter.datasource.BankDataSource
import tut.springboot.starter.model.Bank
import java.lang.IllegalArgumentException

@Repository
class MockBankDataSource : BankDataSource {
    private val banks = mutableListOf(
        Bank(
            "1234",
            3.14,
            17
        ),
        Bank(
            "1010",
            3.14,
            17
        ),
        Bank(
            "5678",
            3.14,
            17
        )
    )

    override fun retrieveBanks(): Collection<Bank> = banks

    override fun retrieveBank(accountNumber: String): Bank = banks.firstOrNull() { it.accountNumber == accountNumber }
        ?: throw NoSuchElementException("Couldn't find bank with account number ($accountNumber)")

    override fun createBank(bank: Bank): Bank {
        if (banks.any { it.accountNumber == bank.accountNumber }){
            throw IllegalArgumentException("Bank with account number ${bank.accountNumber} already exists")
        }
        banks.add(bank)
        return bank
    }
}