package server

fun main() {
    DaggerAppComponent
        .create()
        .application()
        .start()
}
