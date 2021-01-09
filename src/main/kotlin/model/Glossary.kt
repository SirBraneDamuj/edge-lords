package model

const val DEFENDER_STRENGTH_PENALTY = 1
const val MAX_MAX_MANA = 10
const val ELEMENTAL_STRENGTH_MODIFIER = 2

enum class Element {
    FIRE,
    HEAVEN,
    EARTH,
    WATER;

    fun strengthModifierAgainst(element: Element?) =
        when {
            element == null -> 0
            (this.ordinal + 1) % 4 == element.ordinal -> ELEMENTAL_STRENGTH_MODIFIER
            (element.ordinal + 1) % 4 == this.ordinal -> -ELEMENTAL_STRENGTH_MODIFIER
            else -> 0
        }
}

enum class Range {
    MELEE,
    RANGED;
}

enum class Speed {
    NORMAL,
    FAST;
}
