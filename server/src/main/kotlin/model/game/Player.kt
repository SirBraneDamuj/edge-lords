package model.game

import model.MAX_MAX_MANA
import model.card.Deck
import model.card.NatialCard
import model.card.SpellCard
import java.lang.IllegalArgumentException
import java.util.*

data class Player(
    val id: String,
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
    fun master() =
        creatures
            .single { it is Master }
                as Master

    fun creatureAtPosition(position: Position) =
        creatures
            .filter { it.position == position }
            .takeUnless { it.isEmpty() }
            ?.single()

    fun incrementManaAndRestore(amountRestored: Int = MAX_MAX_MANA) {
        maxMana = (maxMana + 1).coerceAtMost(MAX_MAX_MANA)
        mana = (mana + amountRestored).coerceAtMost(maxMana)
    }

    fun spendMana(amount: Int) {
        if (mana < amount) {
            throw IllegalArgumentException("I can't spend more mana than I have...")
        }
        mana -= amount
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
        id: String,
        label: PlayerLabel,
        deck: Deck
    ): Player {
        val gameCards = deck.cards
            .map {
                when (it) {
                    is NatialCard -> GameNatialCard(
                        id = UUID.randomUUID().toString(),
                        cardName = it.name,
                        manaCost = it.manaCost
                    )
                    is SpellCard -> GameSpellCard(
                        id = UUID.randomUUID().toString(),
                        cardName = it.name,
                        manaCost = it.manaCost
                    )
                    else -> error("a player's deck should only contain natials and spells")
                }
            }
            .shuffled()
        val masterCreature = Master(
            id = UUID.randomUUID().toString(),
            card = GameMasterCard(
                id = UUID.randomUUID().toString(),
                cardName = deck.master.name
            ),
            position = Position.BACK_TWO,
            activationState = ActivationState.READY,
            attack = deck.master.attack,
            hp = deck.master.hp,
            maxHp = deck.master.hp,
            range = deck.master.range,
            canUseSkill = if (deck.master.targetingMode == EffectTargetingMode.NONE) null else true
        )
        return Player(
            id = id,
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
