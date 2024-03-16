package timurkhabibulin.passwordmanager.data

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import timurkhabibulin.passwordmanager.data.db.AppDatabase
import timurkhabibulin.passwordmanager.data.db.WebSitesRepositoryImpl
import timurkhabibulin.passwordmanager.domain.repo.PasswordRepository
import timurkhabibulin.passwordmanager.domain.repo.WebSitesRepository
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    @Singleton
    fun bindPasswordRepository(impl: PasswordRepositoryImpl): PasswordRepository

    companion object {
        @Provides
        @Singleton
        fun provideSharedPrefs(@ApplicationContext context: Context): SharedPreferences {
            val masterKey = MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()
            return EncryptedSharedPreferences.create(
                context,
                "passwords",
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        }

        @Provides
        @Singleton
        fun provideWebSitesRepository(appDatabase: AppDatabase): WebSitesRepository {
            return WebSitesRepositoryImpl(appDatabase.webSitesDao())
        }

        @Provides
        @Singleton
        fun provideAppDB(@ApplicationContext context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "AppDatabase"
            ).build()
        }
    }
}
