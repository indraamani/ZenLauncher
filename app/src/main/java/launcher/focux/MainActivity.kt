package launcher.focux

import android.content.Context
import android.content.Intent
import android.content.pm.LauncherApps
import android.content.res.Configuration
import android.os.Bundle
import android.os.UserHandle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.visible
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.launch
import launcher.focux.activity.DrawerActivity
import launcher.focux.activity.SettingActivity
import launcher.focux.datastore.app.ApplicationRepo
import launcher.focux.datastore.userpreference.PreferenceRepo
import launcher.focux.ui.screen.HiddenScreen
import launcher.focux.ui.theme.FocuxTheme
import launcher.focux.ui.component.widget.BoxedClock
import launcher.focux.ui.component.widget.Clock
import launcher.focux.ui.component.widget.DateClockWidget
import launcher.focux.ui.component.widget.DateWidget
import launcher.focux.ui.component.widget.DayClockWidget
import launcher.focux.ui.component.widget.DayWidget
import launcher.focux.ui.component.widget.MonthGrid
import launcher.focux.ui.component.widget.YearGrid
import launcher.focux.utils.Packages
import launcher.focux.utils.TopWidget.*
import launcher.focux.utils.sort
import launcher.focux.viewmodel.MainViewmodel

class MainActivity : ComponentActivity() {

    private val viewModel : MainViewmodel by viewModels()

    private lateinit var launcherApps : LauncherApps
    private val launcherCallback = object : LauncherApps.Callback() {
        override fun onPackageAdded(p0: String?, p1: UserHandle?) {
            CoroutineScope(Dispatchers.IO).launch {
                ApplicationRepo(this@MainActivity)
                    .update(
                        Packages(this@MainActivity)
                            .fetchAllPackages().sort()
                    )
            }
        }

        override fun onPackageChanged(p0: String?, p1: UserHandle?) {
            CoroutineScope(Dispatchers.IO).launch {
                ApplicationRepo(this@MainActivity)
                    .update(
                        Packages(this@MainActivity)
                            .fetchAllPackages().sort()
                    )
            }
        }

        override fun onPackageRemoved(p0: String?, p1: UserHandle?) {
            CoroutineScope(Dispatchers.IO).launch {
                ApplicationRepo(this@MainActivity)
                    .update(
                        Packages(this@MainActivity)
                            .fetchAllPackages().sort()
                    )
            }
        }

        override fun onPackagesAvailable(
            p0: Array<out String?>?,
            p1: UserHandle?,
            p2: Boolean
        ) {

        }

        override fun onPackagesUnavailable(
            p0: Array<out String?>?,
            p1: UserHandle?,
            p2: Boolean
        ) {

        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        lifecycleScope.launch {
            val insetController = WindowCompat.getInsetsController(window, window.decorView)
            insetController.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

            viewModel.setting.collect {
                if(!it!!.showStatusBar) {
                    insetController.hide(WindowInsetsCompat.Type.statusBars())
                } else {
                    insetController.show(WindowInsetsCompat.Type.statusBars())
                }
            }
        }

        lifecycleScope.launch(Dispatchers.IO) {
            val isFirstTime = PreferenceRepo(this@MainActivity).checkAndSetFirstLaunch()
            if (isFirstTime) {
                ApplicationRepo(this@MainActivity)
                    .update(
                        Packages(this@MainActivity)
                            .fetchAllPackages()
                            .sort()
                    )
            }
        }

        launcherApps = getSystemService(Context.LAUNCHER_APPS_SERVICE) as LauncherApps
        launcherApps.registerCallback(launcherCallback)


        setContent {
            FocuxTheme {
                if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
                    MainScreen(viewModel)
                else
                    HiddenScreen()
            }
        }

        onBackPressedDispatcher.addCallback(this@MainActivity, OnBackPressed)
    }

    object OnBackPressed: OnBackPressedCallback(enabled = true) {
        override fun handleOnBackPressed() {
            return
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        launcherApps.unregisterCallback(launcherCallback)
    }
}

@Composable
fun MainScreen(viewmodel: MainViewmodel) {
    val pinnedAppList by viewmodel.pinnedApp.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var hasTriggered by remember { mutableStateOf(false) }
    val setting by viewmodel.setting.collectAsStateWithLifecycle()
    val font = setting!!.font

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
//            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
//            .padding(top = 110.dp, bottom = 40.dp)
            .combinedClickable(
                indication = null,
                interactionSource = null,
                onLongClick = {
                    context.startActivity(
                        Intent(
                            context,
                            SettingActivity::class.java
                        )
                    )
                },
                onClick = {}
            )
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {
                        hasTriggered = false
                    },
                    onDrag = { change, dragAmount ->
                        if (!hasTriggered && change.previousPosition.y > change.position.y && dragAmount.y < 4) {
                            hasTriggered = true
                            context.startActivity(Intent(context, DrawerActivity::class.java))
                        }
                    }
                )
            }

    ) {
        Box(
            modifier = Modifier
                .visible(setting!!.showClock)
                .padding(top = 126.dp)
        ) {
            when(setting!!.topWidget) {
                DEFAULT -> {

                }
                BOXED_CLOCK -> {
                    BoxedClock(font, setting!!.clockFormat)
                }
                CLOCK -> {
                    Clock(font, setting!!.clockFormat)
                }
                DAY -> {
                    DayWidget(font)
                }
                DATE -> {
                    DateWidget(font)
                }
                DAYCLOCK -> {
                    DayClockWidget(font, setting!!.clockFormat)
                }
                DATECLOCK -> {
                    DateClockWidget(font, setting!!.clockFormat)
                }
                HOURGRID -> {
                    HOURGRID
                }
                MONTHGRID -> {
                    MonthGrid()
                }
                YEARGRID -> {
                    YearGrid()
                }
            }
        }

        Spacer(modifier = Modifier.weight(0.8f))
        LazyColumn(
            modifier = Modifier
                .wrapContentWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            items(pinnedAppList) {
                Text(
                    maxLines = 1,
                    text = it.name,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 20.sp,
                    fontFamily = FontFamily(
                        Font( setting!!.font)
                    ),
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(0.dp, 7.dp)
                        .combinedClickable(
                            interactionSource = null,
                            indication = null,
                            onClick = {
                                context.startActivity(
                                    context.packageManager.getLaunchIntentForPackage(it.packageName)
                                )
                            }
                        ),
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .padding(bottom = 40.dp)
        ) {
            DayWidget()
        }

    }
}