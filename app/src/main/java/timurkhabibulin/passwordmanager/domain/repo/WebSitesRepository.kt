package timurkhabibulin.passwordmanager.domain.repo

import timurkhabibulin.passwordmanager.data.db.WebSiteDBEntity

interface WebSitesRepository {
    suspend fun getAllWebSites(): List<WebSiteDBEntity>
    suspend fun addWebSite(webSiteDBEntity: WebSiteDBEntity): Long
    suspend fun updateWebSite(webSiteDBEntity: WebSiteDBEntity)
    suspend fun fundWebSiteById(uid: Long): WebSiteDBEntity
}

