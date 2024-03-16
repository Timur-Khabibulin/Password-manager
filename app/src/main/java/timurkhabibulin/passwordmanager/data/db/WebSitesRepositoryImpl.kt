package timurkhabibulin.passwordmanager.data.db

import timurkhabibulin.passwordmanager.domain.repo.WebSitesRepository
import javax.inject.Inject

class WebSitesRepositoryImpl @Inject constructor(
    private val webSitesDao: WebSitesDao
) : WebSitesRepository {
    override suspend fun getAllWebSites(): List<WebSiteDBEntity> {
        return webSitesDao.getAll()
    }

    override suspend fun addWebSite(webSiteDBEntity: WebSiteDBEntity): Long {
        return webSitesDao.insertWebSite(webSiteDBEntity)
    }

    override suspend fun updateWebSite(webSiteDBEntity: WebSiteDBEntity) {
        webSitesDao.updateWebSite(webSiteDBEntity)
    }

    override suspend fun fundWebSiteById(uid: Long): WebSiteDBEntity {
        return webSitesDao.findWebSiteById(uid)
    }
}
