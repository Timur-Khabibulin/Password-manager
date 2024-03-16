package timurkhabibulin.passwordmanager.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface WebSitesDao {
    @Query("SELECT * FROM websites")
    suspend fun getAll(): List<WebSiteDBEntity>

    @Insert
    suspend fun insertWebSite(webSiteDBEntity: WebSiteDBEntity): Long

    @Update
    suspend fun updateWebSite(webSiteDBEntity: WebSiteDBEntity)

    @Query("SELECT * FROM websites WHERE uid LIKE :uid")
    suspend fun findWebSiteById(uid: Long): WebSiteDBEntity
}
