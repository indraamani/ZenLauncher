package launcher.focux.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import launcher.focux.R

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BottomScroll() {
    Row(
        modifier = Modifier
            .height(132.dp)
            .fillMaxWidth()
            .background(Color.Transparent),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Absolute.SpaceEvenly
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Icon(
                modifier = Modifier
                    .size(60.dp)
                    .clip(
                        RoundedCornerShape(60.dp)
                    )
                    .background(Color.White.copy(alpha = 0.5f))
                    .padding(20.dp),
                tint = Color.White,
                painter = painterResource(R.drawable.lucide_palette),
                contentDescription = null
            )
            Text(
                text = "Wallpaper",
                fontSize = 14.sp,
                color = Color.White
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Icon(
                modifier = Modifier
                    .size(60.dp)
                    .clip(
                        RoundedCornerShape(60.dp)
                    )
                    .background(Color.White.copy(alpha = 0.5f))
                    .padding(20.dp),
                tint = Color.White,
                painter = painterResource(R.drawable.lucide_chart_no_axes_gantt),
                contentDescription = null
            )
            Text(
                text = "Widgets",
                fontSize = 14.sp,
                color = Color.White
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Icon(
                modifier = Modifier
                    .size(60.dp)
                    .clip(
                        RoundedCornerShape(60.dp)
                    )
                    .background(Color.White.copy(alpha = 0.5f))
                    .padding(20.dp),
                tint = Color.White,
                painter = painterResource(R.drawable.lucide_list_end),
                contentDescription = null
            )
            Text(
                text = "Mode",
                fontSize = 14.sp,
                color = Color.White
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Icon(
                modifier = Modifier
                    .size(60.dp)
                    .clip(
                        RoundedCornerShape(60.dp)
                    )
                    .background(Color.White.copy(alpha = 0.5f))
                    .padding(20.dp),
                tint = Color.White,
                painter = painterResource(R.drawable.lucide_settings),
                contentDescription = null
            )
            Text(
                text = "Setting",
                fontSize = 14.sp,
                color = Color.White
            )
        }
    }
}