package server.error

class InvalidRequestError() : RuntimeException(
    "I don't understand this request."
) {
}