package server.error

class InvalidSessionError : RuntimeException(
    "This session does not exist"
)
