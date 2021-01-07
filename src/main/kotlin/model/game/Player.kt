package model.game

import kotlinx.serialization.Serializable
import model.card.Deck
import model.card.NatialCard
import model.card.SpellCard

@Serializable
data class Player(
    val name: String,
    val playerLabel: PlayerLabel,
    var mulliganed: Boolean,
    var creatures: List<Creature>,
    var magicCrystals: Set<Position>,
    var hand: List<GameCard>,
    var deck: List<GameCard>,
    var discard: List<GameCard>,
    var mana: Int,
    var maxMana: Int
) {
    fun creatureAtPosition(position: Position) =
        creatures
            .filter { it.position == position }
            .takeUnless { it.isEmpty() }
            ?.single()

    fun incrementManaAndRestore(amountRestored: Int = 10) {
        maxMana = (maxMana + 1).coerceAtMost(10)
        mana = (mana + amountRestored).coerceAtMost(maxMana)
    }
}

enum class PlayerLabel {
    FIRST,
    SECOND;

    val other: PlayerLabel
        get() = when (this) {
            FIRST -> SECOND
            SECOND -> FIRST
        }
}

object Players {
    fun createPlayerForDeck(
        name: String,
        label: PlayerLabel,
        deck: Deck
    ): Player {
        val gameCards = deck.cards
            .map {
                when (it) {
                    is NatialCard -> GameNatialCard(it.name, it.manaCost)
                    is SpellCard -> GameSpellCard(it.name, it.manaCost)
                    else -> error("a player's deck should only contain natials and spells")
                }
            }
            .shuffled()
        val masterCreature = Master(
            card = GameMasterCard(deck.master.name),
            position = Position.BACK_TWO,
            activationState = ActivationState.READY,
            attack = deck.master.attack,
            hp = deck.master.hp,
            maxHp = deck.master.hp
        )
        return Player(
            name = name,
            playerLabel = label,
            mulliganed = false,
            creatures = listOf(masterCreature),
            magicCrystals = when (label) {
                PlayerLabel.FIRST -> setOf()
                PlayerLabel.SECOND -> setOf(Position.randomStartingMagicCrystalLocation())
            },
            hand = gameCards.take(3),
            deck = gameCards.drop(3),
            discard = listOf(),
            mana = deck.master.mana,
            maxMana = deck.master.mana
        )
    }
}
