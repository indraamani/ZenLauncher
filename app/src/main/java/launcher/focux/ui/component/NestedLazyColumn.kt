package launcher.focux.ui.component

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import launcher.focux.datastore.lockedapp.LockedAppRepo
import launcher.focux.utils.AppModel
import launcher.focux.viewmodel.DrawerViewmodel
import java.util.Locale
import androidx.compose.ui.platform.LocalLocale

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun NestedLazyColumn(
    modifier: Modifier,
    viewmodel: DrawerViewmodel,
    font: Int,
    apps: Map<String, List<AppModel>>,
    bottomSheet: BottomSheetScaffoldState
) {
    // remove
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val lockedApp = viewmodel.lockedApp.collectAsStateWithLifecycle().value

    LazyColumn(
        contentPadding = PaddingValues(bottom = 12.dp),
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        apps.forEach { (word, listOfApps) ->
            item (key = word){
                Text(
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(24.dp, 4.dp),
                    text = word,
                    fontFamily = FontFamily(
                        Font(font)
                    )
                )
            }

            items(
                items = listOfApps,
                key = { app -> app.packageName}
            ){ packages ->
                Text(
                    text = packages.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(LocalLocale.current.platformLocale) else it.toString() },
                    fontSize = 16.sp,
                    fontFamily = FontFamily(
                        Font(font)
                    ),
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 12.dp)
                        .combinedClickable(
                            enabled = true,
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = {
                                coroutineScope.launch {
                                    val app =
                                        lockedApp.filter { app -> app.packageName == packages.packageName }

                                    if (app.isNotEmpty()) {
                                        val isLocked = withContext(Dispatchers.IO) {
                                            LockedAppRepo(context).hasTimePassed(packages.packageName)
                                        }
                                        if (!isLocked) {
                                            Toast.makeText(context, "App Locked", Toast.LENGTH_SHORT).show()
                                            return@launch
                                        } else {
                                            withContext(Dispatchers.IO) {
                                                LockedAppRepo(context).remove(packages.packageName)
                                            }
                                            context.startActivity(
                                                context.packageManager.getLaunchIntentForPackage(
                                                    packages.packageName
                                                )
                                            )
                                            (context as Activity).finish()
                                        }
                                    } else {
                                        context.startActivity(
                                            context.packageManager.getLaunchIntentForPackage(
                                                packages.packageName
                                            )
                                        )
                                        (context as Activity).finish()
                                    }
                                }
                            },
                            onLongClick = {
                                coroutineScope.launch {
                                    viewmodel.selectedApp.value = AppModel(
                                        packages.name,
                                        packages.packageName
                                    )
                                    bottomSheet.bottomSheetState.expand()
                                }
                            }
                        )
                )
            }
        }
    }
}