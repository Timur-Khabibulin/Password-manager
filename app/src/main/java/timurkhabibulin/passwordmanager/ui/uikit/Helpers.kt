package timurkhabibulin.passwordmanager.ui.uikit

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch


@Composable
fun <T> CollectAndShowToast(
    value: SharedFlow<T?>,
    message: (T?) -> String
) {
    val ctx = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(key1 = null, block = {
        coroutineScope.launch {
            value.collect {
                Toast.makeText(ctx, message(it), Toast.LENGTH_SHORT).show()
            }
        }
    })
}
