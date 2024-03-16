package timurkhabibulin.passwordmanager.domain.useCases

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timurkhabibulin.passwordmanager.domain.repo.PasswordRepository
import timurkhabibulin.passwordmanager.domain.repo.WebSitesRepository
import timurkhabibulin.passwordmanager.domain.entities.Account
import timurkhabibulin.passwordmanager.domain.entities.WebSite
import timurkhabibulin.passwordmanager.domain.exceptions.InvalidPasswordException
import javax.inject.Inject

class GetAccountUseCase @Inject constructor(
    private val webSitesRepository: WebSitesRepository,
    private val passwordRepository: PasswordRepository,
    private val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(uid: Long, masterPassword: String): Result<Account> =
        withContext(dispatcher) {
            val result = passwordRepository.validateMasterPassword(masterPassword)
            if (!result) return@withContext Result.failure<Account>(InvalidPasswordException())

            val webSite = webSitesRepository.fundWebSiteById(uid)
            val password = passwordRepository.getPassword(uid)

            return@withContext Result.success(
                Account(
                    WebSite.fromAccountDBEntity(webSite),
                    password
                )
            )
        }
}
