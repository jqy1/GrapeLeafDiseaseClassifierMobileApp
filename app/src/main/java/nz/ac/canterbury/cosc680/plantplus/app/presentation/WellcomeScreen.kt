package nz.ac.canterbury.cosc680.plantplus.app.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import nz.ac.canterbury.cosc680.plantplus.LocalWorkSpackeViewModel
import nz.ac.canterbury.cosc680.plantplus.PlantApp
import nz.ac.canterbury.cosc680.plantplus.R
import nz.ac.canterbury.cosc680.plantplus.app.navigation.Screen
import nz.ac.canterbury.cosc680.plantplus.app.presentation.components.UsableButton
import nz.ac.canterbury.cosc680.plantplus.app.presentation.components.TopBar

@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun WellcomeScreen(
    navController: NavController, modifier: Modifier = Modifier
) {

    //val scope = rememberCoroutineScope()
    val state = rememberScrollState()


    LaunchedEffect(Unit) {
        state.animateScrollTo(100)
        //serviceViewModel.currentWeacher()
    }

    Scaffold(
        topBar = {
            TopBar(stringResource(R.string.main_screen_name), isMainScreen = true, onClick = {})
        },
        //bottomBar = { NavGraph(navController = navController) },

    ){ innerPadding ->
        /* if no workspace in current user, show welcome screen, otherwise show workspace screen */
        Column( modifier = modifier.padding(8.dp) ) {
            WellcomeCard(navController, modifier = modifier)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WellcomeCard(navController: NavController,modifier: Modifier = Modifier){

    Column(modifier=modifier.padding(8.dp)) {
        Spacer(modifier = Modifier.padding(top = 100.dp))

        UsableButton(text = stringResource(id = R.string.wellcome),onClick = {
            navController.navigate( Screen.LoginPageScreen.route)
        })

    }
}








