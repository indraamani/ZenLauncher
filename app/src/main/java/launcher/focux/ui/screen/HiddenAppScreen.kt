package launcher.focux.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import launcher.focux.R
import launcher.focux.datastore.app.ApplicationRepo
import launcher.focux.datastore.pinnedapp.PinnedAppRepo
import launcher.focux.utils.AppModel
import launcher.focux.viewmodel.SettingViewmodel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HiddenAppScreen(
    viewmodel: SettingViewmodel,
    closeScreen: () -> Unit
) {
    val font = viewmodel.setting.collectAsStateWithLifecycle().value.font
    val ctx = LocalContext.current
    val coroutine = rememberCoroutineScope()
    val apps = viewmodel.packages.collectAsStateWithLifecycle().value.allPackages.values.flatten()
        .filter {
            it.isHidden
        }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = {
                            closeScreen()
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.arrow),
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            Text(
                text = "Choose Font",
                modifier = Modifier
                    .padding(horizontal = 12.dp, 30.dp),
                fontSize = 28.sp,
                fontFamily = FontFamily(
                    Font(
                        font
                    )
                )
            )
            if (apps.isEmpty()) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "No Hidden Apps",
                        fontSize = 16.sp,
                        fontFamily = FontFamily(
                            Font(
                                font
                            )
                        )
                    )
                }
            } else {
                LazyColumn {
                    items(
                        apps
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(bottom = 10.dp)
                                .fillMaxWidth()
                                .height(74.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                modifier = Modifier
                                    .padding(16.dp),
                                painter = painterResource(R.drawable.lucide_chart_no_axes_gantt),
                                contentDescription = null
                            )
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(0.5f)
                                    .height(62.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .padding(10.dp)
                                        .fillMaxSize(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = it.name,
                                        fontSize = 16.sp,
                                        fontFamily = FontFamily(
                                            Font(
                                                font
                                            )
                                        )
                                    )
                                }
                            }
                            FloatingActionButton(
                                onClick = {
                                    coroutine.launch(Dispatchers.IO) {
                                        ApplicationRepo(ctx).unhide(
                                            AppModel(
                                                it.name,
                                                it.packageName,
                                                false
                                            )
                                        )
                                    }
                                },
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.lucide_trash),
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}