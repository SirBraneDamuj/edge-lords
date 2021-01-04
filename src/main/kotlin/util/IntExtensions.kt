package util

fun <T> Int.whenIts(
    odd: (Int) -> T,
    even: (Int) -> T
): T {
    return when (this.rem(2)) {
        0 -> odd(this)
        1 -> even(this)
        else -> error("this is impossible")
    }
}
