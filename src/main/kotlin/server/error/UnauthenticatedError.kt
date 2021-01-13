package server.error

import java.lang.RuntimeException

class UnauthenticatedError : RuntimeException(
    "Unauthenticated."
) {
}
