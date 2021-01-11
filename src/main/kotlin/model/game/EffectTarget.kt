package model.game

enum class EffectTargetingMode {
    NONE,
    HAND,
    SINGLE,
    ROW,
    ALL;
}

sealed class EffectTarget<T> {
    abstract val target: T

    class NoTarget : EffectTarget<Unit>() {
        override val target: Unit = Unit
    }

    class HandTarget(
        override val target: Int
    ) : EffectTarget<Int>()

    class SingleTarget(
        override val target: Position
    ) : EffectTarget<Position>()

    class RowTarget(
        override val target: Row
    ) : EffectTarget<Row>()

    class AllTarget : EffectTarget<Set<Position>>() {
        override val target = Position.values().toSet()
    }
}
