package nz.ac.canterbury.cosc680.plantplus.app.presentation.components

import android.graphics.fonts.FontFamily
import androidx.compose.foundation.MagnifierStyle.Companion.Default
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@ExperimentalMaterial3Api
@Composable
fun UserInfoRow(
    label: String,
    value: String,
) {
    Row {  // 1
        Text(
            text = label,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
            )
        )
        Spacer( // 2
            modifier = Modifier.width(20.dp),
        )
        Text(
            text = value,
            style = TextStyle(

                fontSize = 20.sp,
            )
        )
    }
}