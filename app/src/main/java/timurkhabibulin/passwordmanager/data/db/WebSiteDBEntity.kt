package timurkhabibulin.passwordmanager.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "websites")
data class WebSiteDBEntity(
    @PrimaryKey(autoGenerate = true) val uid: Long,
    val url: String,
    val login: String
)
