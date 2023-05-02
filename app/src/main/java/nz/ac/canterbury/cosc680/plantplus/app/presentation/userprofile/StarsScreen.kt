package nz.ac.canterbury.cosc680.plantplus.app.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import nz.ac.canterbury.cosc680.plantplus.R
import nz.ac.canterbury.cosc680.plantplus.app.navigation.Screen
import nz.ac.canterbury.cosc680.plantplus.app.presentation.components.ScreenBottomBar
import nz.ac.canterbury.cosc680.plantplus.app.presentation.components.TopBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun StarsScreen(
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            TopBar(title = stringResource(id = R.string.stars), onClick = {
                navController.navigate(Screen.UserProfileScreen.route)
            })
        },
        bottomBar = {
            ScreenBottomBar(navController)
        }
    ) {
        StarsScreenBody(navController = navController)

    }
}

@Composable
fun StarsScreenBody(modifier: Modifier = Modifier,navController: NavHostController) {

    Column(modifier = modifier.fillMaxWidth()) {
//        Spacer(modifier = Modifier.padding(top = 50.dp))
//
//        ScreenCard(stringResource(id = R.string.possibleresult), onClick = { println(111111) })
//
//        Spacer(modifier = Modifier.padding(top = 10.dp))
//
//        ScreenCard(stringResource(id = R.string.ratetheresult),onClick = { navController.navigate(Screen.ResultScreen.route)})


    }
}