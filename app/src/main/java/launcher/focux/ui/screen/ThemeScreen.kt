package launcher.focux.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import launcher.focux.datastore.userpreference.PreferenceRepo
import launcher.focux.ui.component.Container
import launcher.focux.ui.theme.BotanicalColorScheme
import launcher.focux.ui.theme.BotanicalLightColorScheme
import launcher.focux.ui.theme.CyberNeonColorScheme
import launcher.focux.ui.theme.CyberNeonLightColorScheme
import launcher.focux.ui.theme.DeepPurpleColorScheme
import launcher.focux.ui.theme.DeepPurpleLightColorScheme
import launcher.focux.ui.theme.EmeraldLightColorScheme
import launcher.focux.ui.theme.EmeraldMidnightColorScheme
import launcher.focux.ui.theme.SunsetAmberColorScheme
import launcher.focux.ui.theme.SunsetAmberLightColorScheme
import launcher.focux.utils.ThemeEnum
import launcher.focux.viewmodel.SettingViewmodel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeScreen(
    viewmodel: SettingViewmodel,
    closeScreen: () -> Unit
) {
    val ctx = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val setting = viewmodel.setting.collectAsStateWithLifecycle().value

    Scaffold(
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
                text = "Choose Theme",
                fontFamily = FontFamily(
                    Font(setting.font)
                ),
                modifier = Modifier
                    .padding(horizontal = 12.dp, 30.dp),
                fontSize = 22.sp,
            )

            LazyColumn(
                contentPadding = PaddingValues(horizontal = 20.dp)
            ) {
                item {
                    Container(onclick = {
                        coroutineScope.launch(Dispatchers.IO) {
                            PreferenceRepo(ctx).changeTheme(ThemeEnum.Default)
                        }
                    }) {
                        Row(
                            modifier = Modifier
                            .background(Color.White.copy(alpha = 0.1f))
                                .fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Wallpaper",
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
                item {
                    Container(onclick = {
                        coroutineScope.launch(Dispatchers.IO) {
                            PreferenceRepo(ctx).changeTheme(ThemeEnum.CyberNeon)
                        }
                    }) {
                        Row(
                            modifier = Modifier
                            .background(
                                if (isSystemInDarkTheme()) {
                                    CyberNeonColorScheme.surface
                                } else {
                                    CyberNeonLightColorScheme.surface
                                }
                            )
                                .fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Cyber Neon",
                                fontSize = 16.sp,
                                modifier = Modifier
                                    .background(MaterialTheme.colorScheme.surface),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
                item {
                    Container(onclick = {
                        coroutineScope.launch(Dispatchers.IO) {
                            PreferenceRepo(ctx).changeTheme(ThemeEnum.EmeraldMidnight)
                        }
                    }) {
                        Row(
                            modifier = Modifier
                            .background(
                                if (isSystemInDarkTheme()) {
                                    EmeraldMidnightColorScheme.surface
                                } else {
                                    EmeraldLightColorScheme.surface
                                }
                            )
                                .fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Emerald Midnight",
                                fontSize = 16.sp,
                                modifier = Modifier
                                    .background(MaterialTheme.colorScheme.surface),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
                item {
                    Container(onclick = {
                        coroutineScope.launch(Dispatchers.IO) {
                            PreferenceRepo(ctx).changeTheme(ThemeEnum.SunsetAmber)
                        }
                    }) {
                        Row(
                            modifier = Modifier
                            .background(
                                if (isSystemInDarkTheme()) {
                                    SunsetAmberColorScheme.surface
                                } else {
                                    SunsetAmberLightColorScheme.surface
                                }
                            )
                                .fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Sunset",
                                fontSize = 16.sp,
                                modifier = Modifier
                                    .background(MaterialTheme.colorScheme.surface),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
                item {
                    Container(onclick = {
                        coroutineScope.launch(Dispatchers.IO) {
                            PreferenceRepo(ctx).changeTheme(ThemeEnum.DeepPurple)
                        }
                    }) {
                        Row(
                            modifier = Modifier
                            .background(
                                if (isSystemInDarkTheme()) {
                                    DeepPurpleColorScheme.surface
                                } else {
                                    DeepPurpleLightColorScheme.surface
                                }
                            )
                                .fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center

                        ) {
                            Text(
                                text = "Deep Purple",
                                fontSize = 16.sp,
                                modifier = Modifier
                                    .background(MaterialTheme.colorScheme.surface),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
                item {
                    Container(onclick = {
                        coroutineScope.launch(Dispatchers.IO) {
                            PreferenceRepo(ctx).changeTheme(ThemeEnum.Botanical)
                        }
                    }) {
                        Row(
                            modifier = Modifier
                            .background(
                                if (isSystemInDarkTheme()) {
                                    BotanicalColorScheme.surface
                                } else {
                                    BotanicalLightColorScheme.surface
                                }
                            )
                                .fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Botanical",
                                fontSize = 16.sp,
                                modifier = Modifier
                                    .background(MaterialTheme.colorScheme.surface),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
        }
    }
}