package model

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import model.card.CardType
import model.card.MasterCard
import model.card.NatialCard
import model.card.SpellCard
import util.ResourceLoader

object Cards {
    private val objectMapper = ObjectMapper(YAMLFactory()).registerKotlinModule()

    val natials: Map<String, NatialCard> =
        ResourceLoader.getResource("/cards/natials.yml")
            .let { objectMapper.readValue<List<NatialCard>>(it) }
            .associateBy(NatialCard::name)

    val spells: Map<String, SpellCard> =
        ResourceLoader.getResource("/cards/spells.yml")
            .let { objectMapper.readValue<List<SpellCard>>(it) }
            .associateBy(SpellCard::name)

    val masters: Map<String, MasterCard> =
        ResourceLoader.getResource("/cards/masters.yml")
            .let { objectMapper.readValue<List<MasterCard>>(it) }
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
