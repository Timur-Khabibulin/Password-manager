package timurkhabibulin.passwordmanager.domain.useCases

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timurkhabibulin.passwordmanager.data.db.WebSiteDBEntity
import timurkhabibulin.passwordmanager.domain.entities.AddPasswordStatus
import timurkhabibulin.passwordmanager.domain.repo.PasswordRepository
import timurkhabibulin.passwordmanager.domain.repo.WebSitesRepository
import timurkhabibulin.passwordmanager.domain.entities.Account
import javax.inject.Inject

class UpdateAccountUseCase @Inject constructor(
    private val passwordRepository: PasswordRepository,
    private val webSitesRepository: WebSitesRepository,
    private val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(account: Account): AddPasswordStatus =
        withContext(dispatcher) {
            if (account.webSite.url.isBlank() ||
                account.webSite.login.isBlank() ||
                account.password.isBlank()
            ) {
                return@withContext AddPasswordStatus.EMPTY_FIELD
            }
            webSitesRepository.updateWebSite(
                WebSiteDBEntity(
                    account.webSite.uid,
                    account.webSite.url,
                    account.webSite.login
                )
            )
            passwordRepository.updatePassword(account.webSite.uid, account.password)
            return@withContext AddPasswordStatus.OK
        }
}
