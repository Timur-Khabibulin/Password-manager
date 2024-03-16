package timurkhabibulin.passwordmanager.ui.screens.masterPassword

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import timurkhabibulin.passwordmanager.domain.entities.AddPasswordStatus
import timurkhabibulin.passwordmanager.ui.uikit.CollectAndShowToast
import timurkhabibulin.passwordmanager.ui.uikit.MasterPasswordDialog
import timurkhabibulin.passwordmanager.ui.uikit.PasswordField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MasterPasswordScreen(
    onClose: () -> Unit,
    viewModel: MasterPasswordViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.master_password))
                }
            )
        }
    ) { paddingValues ->
        if (viewModel.passwordExists.value) {
            ExistingPassword(
                modifier = Modifier.padding(paddingValues),
                onClose = onClose,
                viewModel = viewModel
            )
        } else {
            MasterPasswordScreen(
                modifier = Modifier.padding(paddingValues),
                password = "",
                onSaveClick = viewModel::savePassword
            )
        }
        val ctx = LocalContext.current
        CollectAndShowToast(value = viewModel.message, message = {
            when (it) {
                AddPasswordStatus.OK -> ctx.getString(R.string.saved)
                AddPasswordStatus.EMPTY_FIELD -> ctx.getString(R.string.empty_field)
                AddPasswordStatus.MISMATCH -> ctx.getString(R.string.password_mismatch)
                null -> ""
            }
        })
    }
}

@Composable
private fun ExistingPassword(
    modifier: Modifier = Modifier,
    onClose: () -> Unit,
    viewModel: MasterPasswordViewModel
) {
    val ctx = LocalContext.current
    val result = viewModel.password.value
    if (result == null || result.isFailure) {
        MasterPasswordDialog(
            onDismiss = {
                onClose()
            },
            onConfirm = {
                viewModel.loadMasterPassword(it)
            }
        )
        result?.onFailure {
            Toast.makeText(ctx, stringResource(id = R.string.invalid_password), Toast.LENGTH_SHORT)
                .show()
        }
    } else {
        result.onSuccess {
            MasterPasswordScreen(
                modifier = modifier,
                password = it,
                onSaveClick = viewModel::updatePassword
            )
        }
    }
}

@Preview
@Composable
fun MasterPasswordScreenPreview() {
    MasterPasswordScreen(
        password = "",
        onSaveClick = { _, _ -> }
    )
}

@Composable
private fun MasterPasswordScreen(
    modifier: Modifier = Modifier,
    password: String,
    onSaveClick: (psw1: String, psw2: String) -> Unit
) {
    var password1 by rememberSaveable {
        mutableStateOf(password)
    }
    var password2 by rememberSaveable {
        mutableStateOf(password)
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PasswordField(value = password1, onValueChange = { password1 = it })
        Column {
            Text(text = stringResource(R.string.repeat_password))
            PasswordField(value = password2, onValueChange = { password2 = it })
        }
        Button(
            onClick = { onSaveClick(password1, password2) }
        ) {
            Text(text = stringResource(id = R.string.save))
        }
    }
}
