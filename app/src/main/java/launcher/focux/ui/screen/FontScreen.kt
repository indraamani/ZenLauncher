package launcher.focux.ui.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import launcher.focux.R
import launcher.focux.datastore.userpreference.PreferenceRepo
import launcher.focux.datastore.userpreference.preferenceDatastore
import launcher.focux.utils.FontResource
import launcher.focux.utils.fetchAllFont
import launcher.focux.viewmodel.SettingViewmodel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FontScreen(
    viewmodel: SettingViewmodel,
    closeScreen: () -> Unit
) {
    val allFont = fetchAllFont()
    val coroutineScope = rememberCoroutineScope()
    val ctx = LocalContext.current
    val font = viewmodel.setting.collectAsStateWithLifecycle().value.font

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

            LazyColumn(
                contentPadding = PaddingValues(start = 20.dp, end = 20.dp, bottom = 20.dp)
            ) {
                items(
                    items = allFont,
                ) {
                    Box(
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                    )
                    Text(
                        text = it.name,
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(
                            Font(it.resource)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(
                                RoundedCornerShape(40.dp)
                            )
                            .background(Color.White.copy(alpha = 0.1f))
                            .padding(vertical = 30.dp)
                            .combinedClickable(
                                enabled = true,
                                interactionSource = null,
                                indication = null,
                                onClick = {
                                    coroutineScope.launch(Dispatchers.IO) {
                                        PreferenceRepo(ctx).changeFont(it.resource)
                                    }
                                }
                            )
                    )
                }
            }
        }
    }
}