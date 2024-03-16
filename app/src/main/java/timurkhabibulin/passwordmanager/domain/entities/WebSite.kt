package timurkhabibulin.passwordmanager.domain.entities

import timurkhabibulin.passwordmanager.data.db.WebSiteDBEntity

data class WebSite(
    val uid: Long,
    val url: String,
    val login: String
) {
    val iconUrl = "https://www.google.com/s2/favicons?domain=$url&sz=512"

    companion object {
        fun fromAccountDBEntity(webSiteDBEntity: WebSiteDBEntity): WebSite {
            return WebSite(
                webSiteDBEntity.uid,
                webSiteDBEntity.url,
                webSiteDBEntity.login
            )
        }
    }
}
