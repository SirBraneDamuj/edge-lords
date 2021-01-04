package model

enum class Element {
    FIRE,
    HEAVEN,
    EARTH,
    WATER;

    fun strengthModifierAgainst(element: Element?) =
        when {
            element == null -> 0
            (this.ordinal + 1) % 4 == element.ordinal -> 2
            (element.ordinal + 1) % 4 == this.ordinal -> -2
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
