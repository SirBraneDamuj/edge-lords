package server.user

import java.util.*
import javax.inject.Inject

class FetchUserService @Inject constructor(
    private val userRepository: UserRepository
) {
    fun findUser(id: UUID) = userRepository.findUser(id)
}
