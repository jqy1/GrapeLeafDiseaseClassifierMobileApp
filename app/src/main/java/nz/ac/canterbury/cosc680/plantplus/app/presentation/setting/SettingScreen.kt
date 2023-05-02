package nz.ac.canterbury.cosc680.plantplus.app.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import nz.ac.canterbury.cosc680.plantplus.LocalWorkSpackeViewModel
import nz.ac.canterbury.cosc680.plantplus.R
import nz.ac.canterbury.cosc680.plantplus.app.navigation.Screen
import nz.ac.canterbury.cosc680.plantplus.app.presentation.components.ScreenBottomBar
import nz.ac.canterbury.cosc680.plantplus.app.presentation.components.ScreenCard
import nz.ac.canterbury.cosc680.plantplus.app.presentation.components.SmallUsableButton
import nz.ac.canterbury.cosc680.plantplus.app.presentation.components.TopBar



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun SettingScreen(
    navController: NavHostController
) {
    val localWorkSpaceViewModel = LocalWorkSpackeViewModel.current
    Scaffold(
        topBar = {
            TopBar(title = stringResource(id = R.string.setting_name), onClick = {
                navController.navigate(Screen.UserProfileScreen.route)
            })
                    SmallUsableButton(text = stringResource(id = R.string.logout), onClick = {
                        localWorkSpaceViewModel.onSetshowlogout(true)
                    })

        },
        bottomBar = {
            ScreenBottomBar(navController)
        }
    ) {
        SettingScreenBody(navController = navController)
    }
}

@Composable
fun SettingScreenBody(modifier: Modifier = Modifier,navController: NavHostController)
{
    Column(modifier = modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.padding(top = 50.dp))

        ScreenCard(stringResource(id = R.string.password_change_or_reset),onClick = { navController.navigate(
            Screen.PasswardchangeScreen.route)})

        Spacer(modifier = Modifier.padding(top = 10.dp))
        ScreenCard(stringResource(id = R.string.personal_info_edit),onClick = { navController.navigate(
            Screen.PersonalinfoScreen.route)})

    }
}



@Composable
fun Popouplogout(
    text: String, onClick: () -> Unit,navController: NavHostController
) {
    val localWorkSpaceViewModel = LocalWorkSpackeViewModel.current
    val popupdialog by localWorkSpaceViewModel.showlogout.collectAsState()
    if(popupdialog){
        AlertDialog(
            onDismissRequest = { /* Don't */ },
            title = {
                Text(text = "Loging Out?")
            },
            text = {
                Text(text)
            },
            confirmButton = {
                Button( onClick = {
                    localWorkSpaceViewModel.onSetshowlogout()


                    navController.navigate(Screen.WellcomeScreen.route)



                    })
                    {
                    Text("Log Out")
                }
            }
        )
    }
}
