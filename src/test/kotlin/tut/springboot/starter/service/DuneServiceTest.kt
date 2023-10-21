package tut.springboot.starter.service

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import tut.springboot.starter.datasource.DuneDataSource

internal class DuneServiceTest() {
    private val dataSource: DuneDataSource = mockk(relaxed = true)
    private val duneService = DuneService(dataSource)

    @Test
    fun `should call it's data source to retrieve banks`() {
        // Act
        duneService.getBanks()
        // Assert
        verify(exactly = 1) { dataSource.retrieveBanks() }

    }
}