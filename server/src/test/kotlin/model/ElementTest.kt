package model

import model.Element.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class ElementTest {
    @Test
    fun `strengthModifierAgainst advantages`() {
        val advantages = listOf(
            (FIRE to HEAVEN),
            (HEAVEN to EARTH),
            (EARTH to WATER),
            (WATER to FIRE)
        )
        advantages.forEach { (first, second) ->
            assertEquals(ELEMENTAL_STRENGTH_MODIFIER, first.strengthModifierAgainst(second))
            assertEquals(-ELEMENTAL_STRENGTH_MODIFIER, second.strengthModifierAgainst(first))
        }
    }

    @Test
    fun `strengthModifierAgainst neutrals`() {
        val neutrals = listOf(
            (FIRE to EARTH),
            (HEAVEN to WATER)
        )
        neutrals.forEach { (first, second) ->
            assertEquals(0, first.strengthModifierAgainst(second))
            assertEquals(0, second.strengthModifierAgainst(first))
        }
    }

    @Test
    fun `strengthModifierAgainst null`() {
        Element.values().forEach {
            assertEquals(0, it.strengthModifierAgainst(null))
        }
    }
}
