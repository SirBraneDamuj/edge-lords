package server.service

import org.mindrot.jbcrypt.BCrypt
import javax.inject.Inject

class PasswordService @Inject constructor() {
    fun hashPw(password: String) =
        BCrypt.hashpw(password, BCrypt.gensalt())

    fun checkPw(password: String, hashed: String) =
        BCrypt.checkpw(password, hashed)
}
