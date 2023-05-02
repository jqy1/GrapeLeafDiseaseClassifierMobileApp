package nz.ac.canterbury.cosc680.plantplus.app.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import nz.ac.canterbury.cosc680.plantplus.R
import nz.ac.canterbury.cosc680.plantplus.app.navigation.Screen
import nz.ac.canterbury.cosc680.plantplus.app.presentation.components.*

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun MainScreen(
    navController: NavController, modifier: Modifier =Modifier
) {



    Scaffold(
        topBar = {

            TopBar(stringResource(R.string.main_screen_name), isMainScreen = true, onClick = {})

//            SmallUsableButton(text = stringResource(id = R.string.logout), onClick = {navController.navigate(
//                    Screen.WellcomeScreen.route) })

        },
        bottomBar = {
            ScreenBottomBar(navController)
        },
        content = {

            /*
            todo here is main screen body --- Login body
             */
            Box(){Column() {
                Spacer(modifier = Modifier.padding(top = 100.dp))
                LargeScreenCard(stringResource(id = R.string.workspace1),onClick = { navController.navigate(
                    Screen.WorkSpaceScreen.route)})
                LargeScreenCard(stringResource(id = R.string.workspace),onClick = { navController.navigate(
                    Screen.AddWorkSpaceScreen.route)})


                Spacer(modifier = Modifier.padding(top = 300.dp))


                UsableButton(text = stringResource(id = R.string.result), onClick = {navController.navigate(
                    Screen.ResultScreen.route) })


            }}



        },

    )
}









