

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import nz.ac.canterbury.cosc680.plantplus.R
import nz.ac.canterbury.cosc680.plantplus.app.navigation.Screen
import nz.ac.canterbury.cosc680.plantplus.app.presentation.components.ScreenBottomBar
import nz.ac.canterbury.cosc680.plantplus.app.presentation.components.ScreenCard
import nz.ac.canterbury.cosc680.plantplus.app.presentation.components.TopBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun ScanScreen(
    navController: NavHostController,modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopBar(title = stringResource(id = R.string.scan), onClick = {
                navController.navigate(Screen.MainScreen.route)
            })
        },
        bottomBar = {
            ScreenBottomBar(navController)
        }
    ) {
        ScanScreenBody(navController = navController)

    }
}

@Composable
fun ScanScreenBody(modifier: Modifier = Modifier,navController: NavHostController) {

    Column(modifier = modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.padding(top = 100.dp))
        ScreenCard(stringResource(id = R.string.take_photo), onClick = {navController.navigate(
            Screen.CameraScreen.route)})

        Spacer(modifier = Modifier.padding(top = 10.dp))

        ScreenCard(stringResource(id = R.string.upload),onClick = {navController.navigate(
            Screen.UploadphotoScreen.route)})

    }
}
