package launcher.focux.activity

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import launcher.focux.ui.component.BottomSheet
import launcher.focux.ui.component.NestedLazyColumn
import launcher.focux.ui.dialog.LockAppPopup
import launcher.focux.ui.dialog.RenamePopup
import launcher.focux.ui.theme.FocuxTheme
import launcher.focux.viewmodel.DrawerViewmodel

class DrawerActivity : ComponentActivity() {
    private val viewModel :  DrawerViewmodel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FocuxTheme {
                DrawerScreen(
                    viewModel
                )
            }
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerScreen(viewmodel : DrawerViewmodel) {
    val context = LocalContext.current
    val bottomSheet = rememberBottomSheetScaffoldState()

    //view model stuff
    val font = viewmodel.setting.collectAsStateWithLifecycle().value.font
    val lockedApp = viewmodel.lockedApp.collectAsStateWithLifecycle().value
    val packages = viewmodel.packages.collectAsStateWithLifecycle().value.allPackages
        .mapValues { (keys, values) ->
            values.filter { apps ->
                !apps.isHidden
            }
        }
        .filterValues {
            it.isNotEmpty()
        }
    val nestedScroll = remember {
        object : NestedScrollConnection {
            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                if (available.y > 100 && bottomSheet.bottomSheetState.currentValue != SheetValue.Expanded) {
                    (context as Activity).finish()
                }
                return super.onPostScroll(consumed, available, source)
            }
        }
    }

    BottomSheetScaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(nestedScroll),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                OutlinedTextField(
                    value = viewmodel.searchText.collectAsStateWithLifecycle().value,
                    onValueChange = {
                        viewmodel.updateSearchText(it)
                    },
                    placeholder = {
                        Text(
                            text = "Search",
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(top = 54.dp, bottom = 27.dp)
                        .onFocusChanged(
                            onFocusChanged = {

                            }
                        ),
                    shape = RoundedCornerShape(
                        54.dp
                    )
                )
            }
        },
        sheetPeekHeight = 0.dp,
        sheetSwipeEnabled = true,
        scaffoldState = bottomSheet,
        sheetContainerColor = MaterialTheme.colorScheme.onPrimary,
        sheetContent = {
            BottomSheet(
                sheetState = bottomSheet,
                viewmodel = viewmodel
            )
        },
        sheetShape = RoundedCornerShape(
            topStart = 16.dp,
            topEnd = 16.dp
        ),
        sheetDragHandle = {
            Box(
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 10.dp)
                    .height(4.dp)
                    .width(40.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.White)
            )
        }
    ) { innerPadding ->
        NestedLazyColumn(
            modifier = Modifier
                .padding(innerPadding),
            viewmodel,
            font = font,
            apps = packages,
            lockedApp = lockedApp,
            bottomSheet = bottomSheet
        )
        RenamePopup(viewmodel)
        LockAppPopup(viewmodel)
    }
}