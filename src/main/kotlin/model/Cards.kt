package model

import ResourceLoader
import com.charleskorn.kaml.Yaml
import kotlinx.serialization.decodeFromString
import model.card.CardType
import model.card.MasterCard
import model.card.NatialCard
import model.card.SpellCard

object Cards {
    private val natials: Map<String, NatialCard> =
        ResourceLoader.getResource("/natials.yml")
            .let { Yaml.default.decodeFromString<List<NatialCard>>(it) }
            .associateBy(NatialCard::name)

    private val spells: Map<String, SpellCard> =
        ResourceLoader.getResource("/spells.yml")
            .let { Yaml.default.decodeFromString<List<SpellCard>>(it) }
            .associateBy(SpellCard::name)

    private val masters: Map<String, MasterCard> =
        ResourceLoader.getResource("/masters.yml")
            .let { Yaml.default.decodeFromString<List<MasterCard>>(it) }
            .associateBy(MasterCard::name)

    fun getNatialByName(name: String) = natials[name]?.copy()
    fun getSpellByName(name: String) = spells[name]?.copy()
    fun getMasterByName(name: String) = masters[name]?.copy()
    fun getCardByNameAndType(name: String, type: CardType) =
        when (type) {
            CardType.NATIAL -> getNatialByName(name)
            CardType.SPELL -> getSpellByName(name)
            CardType.MASTER -> getMasterByName(name)
        }
}
