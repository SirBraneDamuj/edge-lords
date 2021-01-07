package util

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class IntExtensionsKtTest {
    @Test
    fun `returns the even block when even`() {
        val i = 624
        val result = i.whenIts(
            odd = { "odd" },
            even = { "even" }
        )
        Assertions.assertEquals("even", result)
    }

    @Test
    fun `returns the odd block when odd`() {
        val i = 593
        val result = i.whenIts(
            odd = { "odd" },
            even = { "even" }
        )
        Assertions.assertEquals("odd", result)
    }
}
