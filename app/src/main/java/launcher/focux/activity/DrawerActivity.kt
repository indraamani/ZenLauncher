package launcher.focux.activity

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import launcher.focux.datastore.app.InstalledPackage
import launcher.focux.ui.component.NestedLazyColumn
import launcher.focux.ui.theme.FocuxTheme
import launcher.focux.viewmodel.DrawerViewmodel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import launcher.focux.ui.component.BottomSheet
import launcher.focux.ui.component.popup.RenamePopup

class DrawerActivity : ComponentActivity() {
    private val viewModel : DrawerViewmodel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FocuxTheme {
                DrawerScreen(this@DrawerActivity, viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerScreen(ctx: Context, viewmodel: DrawerViewmodel) {
    val bottomSheet = rememberBottomSheetScaffoldState()
    val nestedScroll = remember {
        object : NestedScrollConnection {
            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                if (available.y > 100 && bottomSheet.bottomSheetState.currentValue != SheetValue.Expanded){
                    (ctx as Activity).finish()
                }
                return super.onPostScroll(consumed, available, source)
            }
        }
    }
    val font = viewmodel.setting.collectAsStateWithLifecycle().value.font
    val allPackage = viewmodel.packages.collectAsStateWithLifecycle().value.allPackages


    BottomSheetScaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(nestedScroll),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Applications",
                    )
                },

            )
        },
        sheetDragHandle = {
            Box(
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 10.dp)
                    .height(4.dp)
                    .width(40.dp)
                    .clip(
                        RoundedCornerShape(
                            20.dp
                        )
                    )
                    .background(Color.White)
            )
        },
        sheetPeekHeight = 0.dp,
        sheetSwipeEnabled = true,
        scaffoldState = bottomSheet,
        sheetContainerColor = MaterialTheme.colorScheme.onSecondaryContainer,
        sheetContent = {
            BottomSheet(bottomSheet, viewmodel)
        },
        sheetShape = RoundedCornerShape(
            topStart = 16.dp,
            topEnd = 16.dp
        )
    ) { innerPadding ->
        NestedLazyColumn(
            modifier = Modifier
                .padding(innerPadding),
            viewmodel,
            font = font,
            apps = allPackage,
            bottomSheet = bottomSheet
        )
        RenamePopup(viewmodel)
    }
}