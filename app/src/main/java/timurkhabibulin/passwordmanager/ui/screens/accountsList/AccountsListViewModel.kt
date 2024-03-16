package timurkhabibulin.passwordmanager.ui.screens.accountsList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import timurkhabibulin.passwordmanager.domain.entities.WebSite
import timurkhabibulin.passwordmanager.domain.useCases.WebSitesUseCase
import javax.inject.Inject

@HiltViewModel
class AccountsListViewModel @Inject constructor(
    private val webSitesUseCase: WebSitesUseCase
) : ViewModel() {
    private val _webSites = MutableStateFlow<List<WebSite>?>(null)
    val webSites: Flow<List<WebSite>> = _webSites.filterNotNull()

     fun loadAccounts() {
        viewModelScope.launch {
            _webSites.value = webSitesUseCase.getAllWebSites()
        }
    }
}
