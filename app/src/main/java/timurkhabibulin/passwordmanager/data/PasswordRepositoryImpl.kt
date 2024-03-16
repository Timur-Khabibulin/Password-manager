package timurkhabibulin.passwordmanager.data

import android.content.SharedPreferences
import androidx.core.content.edit
import timurkhabibulin.passwordmanager.domain.repo.PasswordRepository
import javax.inject.Inject

class PasswordRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : PasswordRepository {

    override suspend fun addPassword(uid: Long, password: String) {
        sharedPreferences.edit {
            putString(uid.toString(), password)
            apply()
        }
    }

    override suspend fun getPassword(uid: Long): String {
        return sharedPreferences.getString(uid.toString(), "") ?: ""
    }

    override suspend fun updatePassword(uid: Long, password: String) {
        sharedPreferences.edit {
            remove(uid.toString())
            apply()
            putString(uid.toString(), password)
            apply()
        }
    }

    override suspend fun addMasterPassword(password: String) {
        sharedPreferences.edit {
            putString(PasswordRepository.MASTER_PASSWORD_KEY, password)
            apply()
        }
    }

    override suspend fun getMasterPassword(): String {
        return sharedPreferences.getString(PasswordRepository.MASTER_PASSWORD_KEY, "") ?: ""
    }

    override suspend fun updateMasterPassword(password: String) {
        sharedPreferences.edit {
            remove(PasswordRepository.MASTER_PASSWORD_KEY)
            apply()
            putString(PasswordRepository.MASTER_PASSWORD_KEY, password)
            apply()
        }
    }

    override suspend fun validateMasterPassword(password: String): Boolean {
        val realPassword =
            sharedPreferences.getString(PasswordRepository.MASTER_PASSWORD_KEY, "") ?: ""
        return password == realPassword
    }

    override suspend fun isMasterPasswordExists(): Boolean {
        return !sharedPreferences.getString(PasswordRepository.MASTER_PASSWORD_KEY, null)
            .isNullOrBlank()
    }
}
