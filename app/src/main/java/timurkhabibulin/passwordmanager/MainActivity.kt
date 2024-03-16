package timurkhabibulin.passwordmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import timurkhabibulin.passwordmanager.ui.screens.accountDetail.AccountDetailScreen
import timurkhabibulin.passwordmanager.ui.screens.accountsList.AccountsListScreen
import timurkhabibulin.passwordmanager.ui.screens.masterPassword.MasterPasswordScreen
import timurkhabibulin.passwordmanager.ui.uikit.theme.PasswordManagerTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PasswordManagerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainNavHost()
                }
            }
        }
    }

    @Composable
    private fun MainNavHost() {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = "WebList"
        ) {
            composable("WebList") {
                AccountsListScreen(
                    onAccountCLick = { webSite ->
                        navController.navigate("AccountDetails/${webSite.uid}")
                    },
                    onAddAccount = {
                        navController.navigate("AccountDetails/-1")
                    },
                    onMasterPasswordClick = {
                        navController.navigate("MasterKey")
                    }
                )
            }
            composable(
                "AccountDetails/{uid}",
                arguments = listOf(navArgument("uid") { type = NavType.LongType })
            ) { backStackEntry ->
                val accountUid = backStackEntry.arguments?.getLong("uid")
                AccountDetailScreen(
                    accountUid = accountUid,
                    onClose = {
                        navController.navigateUp()
                    }
                )
            }
            composable("MasterKey") {
                MasterPasswordScreen(onClose = { navController.navigateUp() })
            }
        }
    }
}
