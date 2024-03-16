package timurkhabibulin.passwordmanager.domain.useCases

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timurkhabibulin.passwordmanager.domain.repo.WebSitesRepository
import timurkhabibulin.passwordmanager.domain.entities.WebSite
import javax.inject.Inject

class WebSitesUseCase @Inject constructor(
    private val webSitesRepository: WebSitesRepository,
    private val dispatcher: CoroutineDispatcher
) {

    suspend fun getAllWebSites(): List<WebSite> = withContext(dispatcher) {
        return@withContext webSitesRepository.getAllWebSites().map { accountDBEntity ->
            WebSite.fromAccountDBEntity(accountDBEntity)
        }
    }
}
