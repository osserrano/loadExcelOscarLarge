package tut.springboot.starter.datasource.mock

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class MockDuneDataSourceTest {
    private val mockDataSource = MockDuneDataSource()

    @Test
    fun `should provide a collection of banks`() {
        // Given

        // When
        val banks = mockDataSource.retrieveBanks()
        // Then
        assertThat(banks).apply {
            isNotEmpty()
            hasSizeGreaterThanOrEqualTo(3)
        }
    }

    @Test
    fun `should provide some mock data`() {
        // Arrange
        // Act
        val banks = mockDataSource.retrieveBanks()
        // Assert
        assertThat(banks).apply {
            allMatch { it.accountNumber.isNotBlank() }
            anyMatch { it.trust != 0.0 }
            anyMatch { it.transactionFee != 0 }
        }
    }
}