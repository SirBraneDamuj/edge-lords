package client

import model.Cards
import model.Range
import model.Speed
import model.card.CardType
import model.game.*
import util.clamp

object ConsolePrinter {
    fun printGame(game: Game) {
        val first = game.player(PlayerLabel.FIRST)
        val second = game.player(PlayerLabel.SECOND)
        printPlayerInfo(second)
        printBoard(first, second)
        printPlayerInfo(first)
    }

    fun printPlayerInfo(player: Player) {
        println("Player: ${player.name}")
        println("Mana: ${player.mana} / ${player.maxMana}")
        println("Deck: ${player.deck.count()} cards remaining")
        println("Hand:")
        printPlayerHand(player)
    }

    fun printPlayerHand(player: Player) {
        var row1 = ""
        var row2 = ""
        var row3 = ""
        for (card in player.hand) {
            row1 += formatGameCardName(card) + " "
            row2 += formatGameCardTypeAndMana(card) + " "
            row3 += if (card is GameNatialCard) {
                formatGameCardStats(card) + " "
            } else {
                "".padEnd(10)
            }
        }
        println(row1)
        println(row2)
        println(row3)
    }

    fun printBoard(first: Player, second: Player) {
        println("/--------------------------------------\\")
        printCreatureRow(second, false)
        printCreatureRow(second, true)
        println("|--------------------------------------|")
        printCreatureRow(first, true)
        printCreatureRow(first, false)
        println("\\--------------------------------------/")
    }

    fun printCreatureRow(player: Player, front: Boolean) {
        val positions = when (front) {
            true -> Position.frontPositions
            false -> Position.backPositions
        }
        val rowStart = when (front) {
            true -> ""
            false -> "".padEnd(5)
        }
        var row1 = rowStart
        var row2 = rowStart
        var row3 = rowStart
        for (position in positions) {
            val creature = player.creatureAtPosition(position)
            row3 += formatPosition(position).padEnd(5)
            when (creature) {
                null -> {
                    row1 += "**".padEnd(9)
                    row2 += "**".padEnd(9)
                    row3 += "".padEnd(4)
                }
                else -> {
                    row1 += formatCreatureName(creature)
                    row2 += formatCreatureStats(creature)
                    row3 += formatStatus(creature)
                }
            }
            row1 += " "
            row2 += " "
            row3 += " "
        }
        println(row1)
        println(row2)
        println(row3)
    }

    // 6 chars
    private fun formatCreatureName(creature: Creature): String {
        val prefix = when (creature) {
            is Natial -> creature.element.toString().take(1)
            is Master -> "M"
        }
        val infix = when {
            creature.speed == Speed.FAST && creature.range == Range.RANGED -> "$"
            creature.speed == Speed.FAST -> "W"
            creature.range == Range.RANGED -> "B"
            else -> " "
        }
        return "$prefix$infix${abbreviateCreatureName(creature.card.cardName)}"
    }

    private fun abbreviateCreatureName(name: String) =
        name.clamp(7)

    // 9 chars
    private fun formatCreatureStats(creature: Creature) =
        "A ${formatCreatureStat(creature.attack)} H ${formatCreatureStat(creature.hp)}"

    private fun formatCreatureStat(i: Int) =
        i.toString().padStart(2)

    private fun formatGameCardName(gameCard: GameCard) =
        gameCard.cardName.clamp(9)

    private fun formatGameCardTypeAndMana(gameCard: GameCard): String {
        val type: String = when (gameCard) {
            is GameSpellCard -> CardType.SPELL.toString()
            is GameNatialCard ->  {
                val card = Cards.getNatialByName(gameCard.cardName) ?: error("this natial don't exist")
                card.element.toString().clamp(7)
            }
            else -> error("no other types should be in hand")
        }
        return "${type.clamp(7)} ${gameCard.manaCost}"
    }

    private fun formatGameCardStats(gameNatialCard: GameNatialCard): String {
        val card = Cards.getNatialByName(gameNatialCard.cardName) ?: error("this natial don't exist")
        return "A ${formatCreatureStat(card.attack)} H ${formatCreatureStat(card.hp)}"
    }

    private fun formatPosition(position: Position): String {
        val row = when (position.backRow) {
            true -> "B"
            false -> "F"
        }
        val num = when (position) {
            Position.FRONT_ONE, Position.BACK_ONE -> "1"
            Position.FRONT_TWO, Position.BACK_TWO -> "2"
            Position.FRONT_THREE, Position.BACK_THREE -> "3"
            Position.FRONT_FOUR -> "4"
        }
        return "$row$num"
    }

    private fun formatStatus(creature: Creature): String {
        val seal = when (creature.sealCount) {
            0 -> "  "
            else -> "S${creature.sealCount.toString().clamp(2)}"
        }
        val guard = when (creature.guardCount) {
            0 -> "  "
            else -> "G${creature.guardCount.toString().clamp(2)}"
        }
        return "$seal$guard"
    }
}
