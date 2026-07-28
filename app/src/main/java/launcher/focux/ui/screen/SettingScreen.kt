package launcher.focux.ui.screen

import android.app.Activity
import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import launcher.focux.R
import launcher.focux.datastore.userpreference.PreferenceRepo
import launcher.focux.ui.component.SettingButton
import launcher.focux.viewmodel.SettingViewmodel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    viewmodel: SettingViewmodel,
    openFontScreen: () -> Unit,
    openTopwidgetScreen: () -> Unit
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val settings = viewmodel.setting.collectAsStateWithLifecycle().value

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Settings",
                        fontSize = 20.sp,
                        modifier = Modifier.padding(start = 6.dp),
                        fontFamily = FontFamily(
                            Font(settings.font)
                        )
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            (context as Activity).finish()
                        }
                    ) {
                        Icon(painter = painterResource(R.drawable.arrow), contentDescription = null)
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
        ) {
            Text(
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp),
                text = "Customisations",
                fontFamily = FontFamily(
                    Font(settings.font)
                )
            )
            SettingButton(
                "Choose Home Theme",
                painterResource(R.drawable.lucide_palette),
                font = settings.font,
                onClick = {
                },
            )
            SettingButton(
                "Choose Font",
                painterResource(R.drawable.lucide_case_sensitive),
                font = settings.font,
                onClick = {
                    openFontScreen()
                },
            )
            SettingButton(
                "Choose Top Widget",
                painterResource(R.drawable.lucide_chart_no_axes_gantt),
                font = settings.font,
                onClick = {
                    openTopwidgetScreen()
                },
            )
            SettingButton(
                "Choose Bottom Widget",
                painterResource(R.drawable.lucide_list_end),
                font = settings.font,
                onClick = { },
            )
            SettingButton(
                "Show Status Bar",
                painterResource(R.drawable.alarm_clock),
                font = settings.font,
                isCheckable = true,
                check = settings.showStatusBar,
                onCheckChange = {
                    coroutineScope.launch(Dispatchers.IO) {
                        PreferenceRepo(context).toggleStatusBar(!settings.showStatusBar)
                    }
                    return@SettingButton null
                }
            )
            SettingButton(
                "Enable Haptic Feedback",
                painterResource(R.drawable.lucide_blend),
                font = settings.font,
                isCheckable = true,
                onCheckChange = { }
            )
            Text(
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp),
                text = "Swipe App Launch Customisations",
                fontFamily = FontFamily(
                    Font(settings.font)
                )
            )
            SettingButton(
                "Enable Swipe to Launch app",
                painterResource(R.drawable.alarm_clock),
                font = settings.font,
                isCheckable = true,
                onCheckChange = { }
            )
            SettingButton(
                "Enable Swipe Up To Open Search",
                painterResource(R.drawable.alarm_clock),
                font = settings.font,
                isCheckable = true,
                onCheckChange = { }
            )
            SettingButton(
                "Choose Swipe Launch Apps",
                painterResource(R.drawable.activity),
                font = settings.font,
                onClick = {

                }
            )
            Text(
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp),
                text = "Clock Customisations",
                fontFamily = FontFamily(
                    Font(settings.font)
                )
            )
            SettingButton(
                "Show Clock on Home",
                painterResource(R.drawable.alarm_clock),
                font = settings.font,
                isCheckable = true,
                check = settings.showClock,
                onCheckChange = {
                    coroutineScope.launch(Dispatchers.IO) {
                        PreferenceRepo(context).toggleClock(!settings.showClock)
                    }
                    return@SettingButton null
                }
            )
            SettingButton(
                "12 Hr Format",
                painterResource(R.drawable.lucide_alarm_clock_off),
                font = settings.font,
                isCheckable = true,
                check = settings.clockFormat,
                onCheckChange = {
                    coroutineScope.launch(Dispatchers.IO) {
                        PreferenceRepo(context).changeClockFormat(!settings.clockFormat)
                    }
                    return@SettingButton null
                }
            )
            Text(
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp),
                text = "Hidden Apps",
                fontFamily = FontFamily(
                    Font(settings.font)
                )
            )
            SettingButton(
                "Modify Hidden Apps",
                painterResource(R.drawable.lucide_ban),
                font = settings.font,
                onClick = {

                }
            )
            Text(
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp),
                text = "Renamed Apps",
                fontFamily = FontFamily(
                    Font(settings.font)
                )
            )
            SettingButton(
                "Modify/View Renamed Apps",
                painterResource(R.drawable.lucide_route),
                font = settings.font,
                onClick = {

                }
            )
            Text(
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp),
                text = "Pinned Apps Customization",
                fontFamily = FontFamily(
                    Font(settings.font)
                )
            )
            SettingButton(
                "Modifed Pinned Apps",
                painterResource(R.drawable.lucide_grid),
                font = settings.font,
                onClick = {

                }
            )

            Row (
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                OutlinedButton(
                    onClick = {
                        val intent = Intent(Settings.ACTION_HOME_SETTINGS)
                        context.startActivity(intent)

                    },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.padding(vertical = 20.dp)
                ) {
                    Text(
                        text = "Set as Default Launcher"
                    )
                }
            }
        }
    }
}