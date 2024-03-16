package timurkhabibulin.passwordmanager.ui.screens.accountDetail

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import timurkhabibulin.passwordmanager.R
import timurkhabibulin.passwordmanager.domain.entities.Account
import timurkhabibulin.passwordmanager.domain.entities.AddPasswordStatus
import timurkhabibulin.passwordmanager.domain.entities.WebSite
import timurkhabibulin.passwordmanager.ui.uikit.CollectAndShowToast
import timurkhabibulin.passwordmanager.ui.uikit.MasterPasswordDialog
import timurkhabibulin.passwordmanager.ui.uikit.PasswordField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountDetailScreen(
    accountUid: Long?,
    onClose: () -> Unit,
    viewModel: AccountDetailViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.account))
                }
            )
        },
    ) { paddingValues ->
        if (accountUid != null && accountUid >= 0) {
            ExistingAccount(
                modifier = Modifier.padding(paddingValues),
                onClose = onClose,
                accountUid = accountUid,
                viewModel = viewModel
            )
        } else {
            NewAccount(
                modifier = Modifier.padding(paddingValues),
                onSaveClick = viewModel::saveAccount
            )
        }

        val ctx = LocalContext.current
        CollectAndShowToast(value = viewModel.saveStatus, message = { status ->
            when (status) {
                AddPasswordStatus.OK -> ctx.getString(R.string.saved)
                AddPasswordStatus.EMPTY_FIELD -> ctx.getString(R.string.empty_field)
                AddPasswordStatus.MISMATCH -> ctx.getString(R.string.password_mismatch)
                null -> ""
            }
        })
    }
}

@Composable
private fun ExistingAccount(
    modifier: Modifier = Modifier,
    onClose: () -> Unit,
    accountUid: Long,
    viewModel: AccountDetailViewModel
) {
    val ctx = LocalContext.current
    val result = viewModel.account.value
    if (result == null || result.isFailure) {
        MasterPasswordDialog(
            onDismiss = {
                onClose()
            },
            onConfirm = {
                viewModel.loadAccount(accountUid, it)
            }
        )
        result?.onFailure {
            Toast.makeText(ctx, stringResource(id = R.string.invalid_password), Toast.LENGTH_SHORT)
                .show()
        }
    } else {
        result.onSuccess { account ->
            AccountDetailScreen(
                modifier = modifier,
                account = account,
                onSaveClick = viewModel::updateAccount
            )
        }
    }
}

@Composable
private fun NewAccount(
    modifier: Modifier = Modifier,
    onSaveClick: (Account) -> Unit
) {
    AccountDetailScreen(
        modifier = modifier,
        account = null,
        onSaveClick = onSaveClick
    )
}

@Preview
@Composable
fun AccountDetailScreenPreview() {
    AccountDetailScreen(
        account = Account(WebSite(1, "url", "login"), "password"),
        onSaveClick = { },
    )
}

@Composable
private fun AccountDetailScreen(
    modifier: Modifier = Modifier,
    account: Account?,
    onSaveClick: (Account) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var url by rememberSaveable { mutableStateOf(account?.webSite?.url ?: "") }
        var login by rememberSaveable { mutableStateOf(account?.webSite?.login ?: "") }
        var password by rememberSaveable { mutableStateOf(account?.password ?: "") }
        TextField(
            value = url,
            onValueChange = { url = it },
            label = {
                Text(text = stringResource(R.string.website_address))
            }
        )
        TextField(
            value = login,
            onValueChange = { login = it },
            label = {
                Text(text = stringResource(R.string.login))
            }
        )
        PasswordField(
            value = password,
            onValueChange = { password = it }
        )
        Button(
            onClick = {
                onSaveClick(
                    Account(
                        WebSite(account?.webSite?.uid ?: 0, url, login),
                        password
                    )
                )
            }
        ) {
            Text(text = stringResource(R.string.save))
        }
    }
}
