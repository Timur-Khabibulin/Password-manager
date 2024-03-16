package timurkhabibulin.passwordmanager.ui.screens.accountDetail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timurkhabibulin.passwordmanager.domain.entities.Account
import timurkhabibulin.passwordmanager.domain.entities.AddPasswordStatus
import timurkhabibulin.passwordmanager.domain.useCases.AddAccountUseCase
import timurkhabibulin.passwordmanager.domain.useCases.GetAccountUseCase
import timurkhabibulin.passwordmanager.domain.useCases.UpdateAccountUseCase
import javax.inject.Inject

@HiltViewModel
class AccountDetailViewModel @Inject constructor(
    private val addAccountUseCase: AddAccountUseCase,
    private val getAccountUseCase: GetAccountUseCase,
    private val updateAccountUseCase: UpdateAccountUseCase
) : ViewModel() {
    private val _saveStatus = MutableSharedFlow<AddPasswordStatus?>()
    val saveStatus = _saveStatus.asSharedFlow()
    private val _account = mutableStateOf<Result<Account>?>(null)
    val account: State<Result<Account>?> = _account

    fun saveAccount(account: Account) {
        viewModelScope.launch {
            val status = addAccountUseCase.invoke(account)
            _saveStatus.emit(status)
        }
    }

    fun updateAccount(account: Account) {
        viewModelScope.launch {
            val status = updateAccountUseCase.invoke(account)
            _saveStatus.emit(status)
        }
    }

    fun loadAccount(uid: Long, masterPassword: String) {
        viewModelScope.launch {
            val result = getAccountUseCase.invoke(uid, masterPassword)
            _account.value = result
        }
    }
}
