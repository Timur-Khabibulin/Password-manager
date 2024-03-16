package timurkhabibulin.passwordmanager.ui.screens.masterPassword

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timurkhabibulin.passwordmanager.domain.entities.AddPasswordStatus
import timurkhabibulin.passwordmanager.domain.useCases.MasterPasswordManagerUseCase
import javax.inject.Inject

@HiltViewModel
class MasterPasswordViewModel @Inject constructor(
    private val masterPasswordManagerUseCase: MasterPasswordManagerUseCase
) : ViewModel() {
    private val _passwordExists = mutableStateOf(false)
    val passwordExists = _passwordExists
    private val _message = MutableSharedFlow<AddPasswordStatus?>()
    val message = _message.asSharedFlow()
    private val _password = mutableStateOf<Result<String>?>(null)
    val password: State<Result<String>?> = _password

    init {
        viewModelScope.launch {
            _passwordExists.value = masterPasswordManagerUseCase.isMasterPasswordExists()
        }
    }

    fun savePassword(psw1: String, psw2: String) {
        ifPasswordGood(psw1, psw2) {
            masterPasswordManagerUseCase.addPassword(it)
            _message.emit(AddPasswordStatus.OK)
        }
    }

    fun updatePassword(psw1: String, psw2: String) {
        ifPasswordGood(psw1, psw2) {
            masterPasswordManagerUseCase.updatePassword(it)
            _message.emit(AddPasswordStatus.OK)
        }
    }

    fun loadMasterPassword(password: String) {
        viewModelScope.launch {
            val result = masterPasswordManagerUseCase.getPassword(password)
            _password.value = result
        }
    }

    private fun ifPasswordGood(
        psw1: String,
        psw2: String,
        block: suspend (psw: String) -> Unit
    ) {
        viewModelScope.launch {
            if (psw1.isBlank()) _message.emit(AddPasswordStatus.EMPTY_FIELD)
            else if (psw1 != psw2) _message.emit(AddPasswordStatus.MISMATCH)
            else {
                block(psw1)
            }
        }
    }
}
