package server.error

import java.lang.RuntimeException

class InvalidSessionError : RuntimeException(
    "This session does not exist"
)
