package nz.ac.canterbury.cosc680.plantplus.app.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@ExperimentalMaterial3Api
@Composable
fun UsableButton(  // 1
    text: String,
    onClick: () -> Unit,
) {
    Column(  // 2
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(  // 3
            onClick = { onClick() },
            modifier = Modifier
                .width(300.dp)
                .height(50.dp),
        ) {
            Text(  // 4
                text = text,
                fontSize = 20.sp,
            )
        }
    }
}