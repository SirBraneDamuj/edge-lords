package util

import kotlin.math.abs

fun <T> Int.whenIts(
    odd: (Int) -> T,
    even: (Int) -> T
): T {
    return when (abs(this.rem(2))) {
        0 -> even(this)
        1 -> odd(this)
        else -> error("this is impossible")
    }
}
