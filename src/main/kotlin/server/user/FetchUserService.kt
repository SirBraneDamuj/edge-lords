package server.user

import javax.inject.Inject

class FetchUserService @Inject constructor(
    private val userRepository: UserRepository
) {
    fun findUser(id: Int) = userRepository.findUser(id)
}
