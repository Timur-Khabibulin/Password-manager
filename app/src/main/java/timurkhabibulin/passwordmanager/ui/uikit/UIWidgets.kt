package timurkhabibulin.passwordmanager.ui.uikit

import androidx.compose.foundation.clickable
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import timurkhabibulin.passwordmanager.R

@Composable
fun PasswordField(
    value: String,
    onValueChange: (String) -> Unit
) {
    var passwordVisible by remember {
        mutableStateOf(false)
    }
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(text = "Пароль")
        },
        visualTransformation = if (passwordVisible) VisualTransformation.None
        else PasswordVisualTransformation(),
        trailingIcon = {
            Icon(
                modifier = Modifier.clickable { passwordVisible = !passwordVisible },
                painter = painterResource(id = R.drawable.visibility),
                contentDescription = "show password"
            )
        }
    )
}


@Preview
@Composable
fun MasterPasswordDialogPreview() {
    MasterPasswordDialog(
        onDismiss = {},
        onConfirm = {}
    )
}

@Composable
fun MasterPasswordDialog(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var password by rememberSaveable {
        mutableStateOf("")
    }
    AlertDialog(
        onDismissRequest = { onDismiss() },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismiss()
                }
            ) {
                Text("Отмена")
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm(password)
                }
            ) {
                Text("Ок")
            }
        },
        title = {
            Text(text = "Введите мастер пароль")
        },
        text = {
            PasswordField(
                value = password,
                onValueChange = { password = it }
            )
        }
    )
}

