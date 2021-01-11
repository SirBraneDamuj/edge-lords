package util

object ResourceLoader {
    fun getResource(path: String) = this::class.java.getResource(path).readText()
}

