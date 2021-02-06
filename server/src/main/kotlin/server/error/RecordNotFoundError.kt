package server.error

class RecordNotFoundError() : RuntimeException(
    "The record requested was not found."
)
