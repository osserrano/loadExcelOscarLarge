package tut.springboot.starter.service

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import tut.springboot.starter.datasource.BankDataSource

internal class BankServiceTest() {
    private val dataSource: BankDataSource = mockk(relaxed = true)
    private val bankService = BankService(dataSource)

    @Test
    fun `should call it's data source to retrieve banks`() {
        // Act
        bankService.getBanks()
        // Assert
        verify(exactly = 1) { dataSource.retrieveBanks() }

    }
}