package timurkhabibulin.passwordmanager.domain.repo

interface PasswordRepository {
    companion object {
        const val MASTER_PASSWORD_KEY = "MASTER_PASSWORD_KEY"
    }

    suspend fun addPassword(uid: Long, password: String)
    suspend fun getPassword(uid: Long): String
    suspend fun updatePassword(uid: Long, password: String)

    suspend fun addMasterPassword(password: String)
    suspend fun getMasterPassword(): String
    suspend fun updateMasterPassword(password: String)
    suspend fun validateMasterPassword(password: String): Boolean
    suspend fun isMasterPasswordExists(): Boolean
}
