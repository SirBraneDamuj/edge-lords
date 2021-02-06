package util

fun String.clamp(length: Int) =
    this.take(length).padEnd(length)

