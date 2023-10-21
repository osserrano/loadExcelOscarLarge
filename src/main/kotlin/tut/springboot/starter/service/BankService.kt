package tut.springboot.starter.service

import org.springframework.stereotype.Service
import tut.springboot.starter.datasource.BankDataSource
import tut.springboot.starter.model.Bank

@Service
class BankService(private val dataSource: BankDataSource) {

    fun getBanks(): Collection<Bank> = dataSource.retrieveBanks()
    fun getBank(accountNumber: String): Bank = dataSource.retrieveBank(accountNumber)
    fun addBank(bank: Bank): Bank = dataSource.createBank(bank)


}