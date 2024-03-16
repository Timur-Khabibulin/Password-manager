package timurkhabibulin.passwordmanager.ui.screens.accountsList

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import timurkhabibulin.passwordmanager.R
import timurkhabibulin.passwordmanager.domain.entities.WebSite


@Composable
fun AccountsListScreen(
    viewModel: AccountsListViewModel = hiltViewModel(),
    onAccountCLick: (WebSite) -> Unit,
    onAddAccount: () -> Unit,
    onMasterPasswordClick: () -> Unit
) {
    viewModel.loadAccounts()
    val accounts = viewModel.webSites.collectAsState(initial = listOf())
    AccountsListScreen(
        webSites = accounts.value,
        onAccountCLick = onAccountCLick,
        onAddAccount = onAddAccount,
        onMasterPasswordClick = onMasterPasswordClick
    )
}

@Preview
@Composable
fun AccountsListScreenPreview() {
    AccountsListScreen(
        webSites = listOf(
            WebSite(1, "https://www.youtube.com/", "login1"),
            WebSite(2, "https://developer.android.com/", "login2")
        ),
        onAccountCLick = {},
        onAddAccount = {},
        onMasterPasswordClick = {}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AccountsListScreen(
    webSites: List<WebSite>,
    onAccountCLick: (WebSite) -> Unit,
    onAddAccount: () -> Unit,
    onMasterPasswordClick: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                }
            )
        },
        floatingActionButton = {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.End
            ) {
                FloatingActionButton(onClick = { onAddAccount() }) {
                    Text(
                        modifier = Modifier.padding(horizontal = 5.dp),
                        text = stringResource(R.string.account)
                    )
                }
                FloatingActionButton(onClick = { onMasterPasswordClick() }) {
                    Text(
                        modifier = Modifier.padding(horizontal = 5.dp),
                        text = stringResource(R.string.master_password)
                    )
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 5.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(webSites) { webSite ->
                WebSiteCard(webSite = webSite, onClick = onAccountCLick)
            }
        }
    }
}

@Composable
private fun WebSiteCard(
    webSite: WebSite,
    onClick: (WebSite) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.secondaryContainer,
                shape = RoundedCornerShape(20.dp)
            )
            .clickable { onClick(webSite) }
            .padding(20.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier.size(30.dp),
            model = webSite.iconUrl,
            contentDescription = stringResource(R.string.web_site_icon)
        )
        Text(text = webSite.url)
    }
}
