package server.error

class UnauthorizedError : RuntimeException(
    "You don't have access to that resource."
)