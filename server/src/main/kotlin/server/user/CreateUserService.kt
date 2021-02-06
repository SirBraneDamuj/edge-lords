package server.user

import server.session.PasswordService
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
