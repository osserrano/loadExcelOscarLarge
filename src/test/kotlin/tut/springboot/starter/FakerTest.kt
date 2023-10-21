package tut.springboot.starter

import com.github.javafaker.Faker
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class FakerTest {

    @Test
    fun `should print Dune quotes`() {
        val message = Faker.instance().dune().quote()
        println(message)
        Assertions.assertThat(message).isNotBlank()
    }
}