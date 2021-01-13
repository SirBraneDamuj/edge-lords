package server.service

import server.repository.UserRepository
import javax.inject.Inject

class FetchUserService @Inject constructor(
    private val userRepository: UserRepository
) {
    fun findUser(id: Int) = userRepository.findUser(id)
}
