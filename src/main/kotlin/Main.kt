import com.charleskorn.kaml.Yaml
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import model.card.MasterCard
import model.card.NatialCard
import model.card.SpellCard

fun main() {
    val masters = Yaml.default.decodeFromString<List<MasterCard>>(ResourceLoader.getResource("/masters.yml"))
    masters.forEach {
        println(Json.encodeToString(it))
    }
    val natials = Yaml.default.decodeFromString<List<NatialCard>>(ResourceLoader.getResource("/natials.yml"))
    natials.forEach {
        println(Json.encodeToString(it))
    }
    val spells = Yaml.default.decodeFromString<List<SpellCard>>(ResourceLoader.getResource("/spells.yml"))
    spells.forEach {
        println(Json.encodeToString(it))
    }
}

object ResourceLoader {
    fun getResource(path: String) = this::class.java.getResource(path).readText()
}

