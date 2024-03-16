package timurkhabibulin.passwordmanager.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [WebSiteDBEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun webSitesDao(): WebSitesDao
}
