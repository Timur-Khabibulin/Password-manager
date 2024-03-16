package timurkhabibulin.passwordmanager.domain.useCases

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timurkhabibulin.passwordmanager.domain.exceptions.InvalidPasswordException
import timurkhabibulin.passwordmanager.domain.repo.PasswordRepository
import javax.inject.Inject

class MasterPasswordManagerUseCase @Inject constructor(
    private val passwordRepository: PasswordRepository,
    private val dispatcher: CoroutineDispatcher
) {

    suspend fun addPassword(password: String) = withContext(dispatcher) {
        passwordRepository.addMasterPassword(password)
    }

    suspend fun updatePassword(password: String) = withContext(dispatcher) {
        passwordRepository.updateMasterPassword(password)
    }

    suspend fun getPassword(password: String): Result<String> = withContext(dispatcher) {
        val result = passwordRepository.validateMasterPassword(password)
        if (!result) return@withContext Result.failure<String>(InvalidPasswordException())
        val psw = passwordRepository.getMasterPassword()
        return@withContext Result.success(psw)
    }

    suspend fun isMasterPasswordExists(): Boolean = withContext(dispatcher) {
        return@withContext passwordRepository.isMasterPasswordExists()
    }
}
