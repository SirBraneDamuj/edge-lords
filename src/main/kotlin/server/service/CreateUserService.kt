package server.service

import server.dto.CreateUserRequest
import server.dto.UserDto
import server.repository.UserRepository
import javax.inject.Inject

class CreateUserService @Inject constructor(
    private val passwordService: PasswordService,
    private val userRepository: UserRepository
) {

    fun createUser(
        createUserRequest: CreateUserRequest
    ): UserDto {
        val hash = passwordService.hashPw(createUserRequest.password)
        val user = userRepository.createUser(
            name = createUserRequest.name,
            hashedPassword = hash
        )
        return UserDto(
            id = user.id.value,
            name = user.name,
            decks = listOf()
        )
    }

}
