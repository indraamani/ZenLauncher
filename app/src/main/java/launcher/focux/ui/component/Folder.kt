package launcher.focux.ui.component

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import launcher.focux.R
import launcher.focux.datastore.lockedapp.LockedAppRepo
import launcher.focux.utils.AppModel
import launcher.focux.viewmodel.DrawerViewmodel

@Composable
fun Folder(viewmodel: DrawerViewmodel) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val text: String = viewmodel.searchText.collectAsStateWithLifecycle().value
    val lockedApp = viewmodel.lockedApp.collectAsStateWithLifecycle().value
    val apps = viewmodel.packages.collectAsStateWithLifecycle().value.allPackages.values.flatten().filter {
        it.name.contains(text)
    }

    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Center
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxHeight(0.55f)
                .fillMaxWidth(0.9f)
                .clip(
                    RoundedCornerShape(
                        12.dp
                    )
                )
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .border(
                        1.dp,
                        MaterialTheme.colorScheme.primary,
                        RoundedCornerShape(
                            12.dp
                    )
                ),
            contentPadding = PaddingValues(12.dp),

        ) {
            items(apps) {
                Text(
                    maxLines = 1,
                    text = it.name,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 20.sp,
                    fontFamily = FontFamily(
                        Font(R.font.comfortaa_bold)
                    ),
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(0.dp, 16.dp)
                        .combinedClickable(
                            interactionSource = null,
                            indication = null,
                            onClick = {
                                coroutineScope.launch {
                                    val app =
                                        lockedApp.filter { app -> it.packageName == app.packageName }

                                    if (app.isNotEmpty()) {
                                        val isLocked = withContext(Dispatchers.IO) {
                                            LockedAppRepo(context).hasTimePassed(it.packageName)
                                        }
                                        if (!isLocked) {
                                            Toast.makeText(context, "App Locked", Toast.LENGTH_SHORT).show()
                                            return@launch
                                        } else {
                                            withContext(Dispatchers.IO) {
                                                LockedAppRepo(context).remove(it.packageName)
                                            }
                                            context.startActivity(
                                                context.packageManager.getLaunchIntentForPackage(
                                                    it.packageName
                                                )
                                            )
                                            (context as Activity).finish()
                                        }
                                    } else {
                                        context.startActivity(
                                            context.packageManager.getLaunchIntentForPackage(
                                                it.packageName
                                            )
                                        )
                                        (context as Activity).finish()
                                    }
                                }
                            }
                        )
                )
                HorizontalDivider()
            }
        }
    }
}