package model.game

data class Game(
    val players: Map<PlayerLabel, Player>,
    val turn: Int
) {
    fun addNatialForPlayer(natial: Natial, playerLabel: PlayerLabel) =
        this.players.getValue(playerLabel).let { player ->
            this.copy(
                players = mapOf(
                    playerLabel to player.copy(
                        hand = player.hand - natial.card,
                        natials = player.natials + natial,
                        mana = player.mana - natial.card.manaCost,
                        magicCrystals = player.magicCrystals - natial.position
                    ),
                    playerLabel.other to this.players.getValue(playerLabel.other)
                )
            )
        }
}
