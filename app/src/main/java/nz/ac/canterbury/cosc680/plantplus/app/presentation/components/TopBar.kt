package nz.ac.canterbury.cosc680.plantplus.app.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nz.ac.canterbury.cosc680.plantplus.R

@ExperimentalMaterial3Api
@Composable
fun TopBar(
    title: String,
    onClick: () -> Unit,
    isMainScreen: Boolean = false,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (!isMainScreen) {
                    IconButton(onClick = { onClick() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_back),
                            contentDescription = null
                        )
                    }
                }

                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,) {
                    Text(  // 4
                        text = title,
                        fontFamily = FontFamily.Cursive,
                        fontSize = 30.sp,
                    )


                }
//                var navController = rememberNavController()
//
//                UsableButton(text = stringResource(id = R.string.logout), onClick = {navController.navigate(
//                    Screen.WellcomeScreen.route) })

            }
        },
    )
}