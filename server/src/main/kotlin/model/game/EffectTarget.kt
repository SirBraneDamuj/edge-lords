package model.game

enum class EffectTargetingMode {
    NONE,
    DECK_SELF,
    GRAVEYARD_SELF,
    EMPTY_ALLY,
    HAND_ENEMY,
    HAND_SELF,
    SINGLE_ENEMY,
    SINGLE_ALLY,
    ROW_ENEMY,
    ROW_ALLY,
    ALL_ENEMY,
    ALL_ALLY,
    ALL;
}
