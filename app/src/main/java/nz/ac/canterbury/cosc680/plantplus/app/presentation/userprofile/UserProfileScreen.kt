package nz.ac.canterbury.cosc680.plantplus.app.presentation

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
fun UserProfileScreen(
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            TopBar(title = stringResource(id = R.string.user_profile_name), onClick = {
                navController.navigate(Screen.MainScreen.route)
            })
        },
        bottomBar = {
            ScreenBottomBar(navController)
        }
    ) {
        UserProfileScreenBody(navController = navController)
    }
}

@Composable
fun UserProfileScreenBody(modifier: Modifier = Modifier,navController: NavHostController) {
    Column(modifier = modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.padding(top = 50.dp))

        ScreenCard(stringResource(id = R.string.scan_history),onClick = {navController.navigate(
            Screen.ScanHistoryScreen.route)})

        Spacer(modifier = Modifier.padding(top = 10.dp))

        ScreenCard(stringResource(id = R.string.plant_states),onClick = {navController.navigate(
            Screen.PlantstateScreen.route)})

        Spacer(modifier = Modifier.padding(top = 10.dp))

        ScreenCard(stringResource(id = R.string.stars),onClick = { navController.navigate(
            Screen.StarsScreen.route)})
    }
}