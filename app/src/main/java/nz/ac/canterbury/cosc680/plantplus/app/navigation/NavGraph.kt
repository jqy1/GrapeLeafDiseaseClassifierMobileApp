package nz.ac.canterbury.cosc680.plantplus.app.navigation

import CameraScreen
import ForgetPasswardScreen
import LogInScreen
import ScanScreen

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import io.ktor.util.reflect.*
import nz.ac.canterbury.cosc680.plantplus.LocalWorkSpackeViewModel
import nz.ac.canterbury.cosc680.plantplus.app.presentation.*
import nz.ac.canterbury.cosc680.plantplus.app.presentation.scan.UploadPhotoScreen
import nz.ac.canterbury.cosc680.plantplus.app.presentation.workspace.DetailedWorkSpaceScreen
import nz.ac.canterbury.cosc680.plantplus.app.presentation.workspace.WorkSpaceScreenScreen

@ExperimentalMaterial3Api
@Composable
fun NavGraph(
    navController: NavHostController
) {
    val localWorkSpaceViewModel = LocalWorkSpackeViewModel.current

    NavHost(navController = navController, startDestination = Screen.WellcomeScreen.route) {
        composable(
            route = Screen.MainScreen.route
        ) {
            MainScreen(navController = navController)
        }

        composable(
            route = Screen.UserProfileScreen.route
        ) {
            UserProfileScreen(navController)
        }

        composable(
            route = Screen.SettingScreen.route
        ) {
            SettingScreen(navController)

        }

        composable(
            route = Screen.ScanHistoryScreen.route
        ) {
            ScanHistoryScreen(navController)

        }
        composable(
                route = Screen.PlantstateScreen.route
                ) {
            PlantstateScreen(navController)

        }
        composable(
                route = Screen.StarsScreen.route
                ) {
            StarsScreen(navController)

        }
        composable(
                route = Screen.PasswardchangeScreen.route
                ) {
            PasswardchangeScreen(navController)

        }
        composable(
                route = Screen.PersonalinfoScreen.route
                ) {
            PersonalinfoScreen(navController)

        }
        composable(
            route = Screen.ScanScreen.route
        ) {
            ScanScreen(navController = navController)
        }
        composable(
            route = Screen.CameraScreen.route
        ) {
            val workSpace = localWorkSpaceViewModel.currentWorkSpace.collectAsState()
            CameraScreen(navController,workSpace.value)

        }
        composable(
            route = Screen.UploadphotoScreen.route
        ) {
            val workSpace = localWorkSpaceViewModel.currentWorkSpace.collectAsState()
            UploadPhotoScreen(navController,workSpace.value)

        }
        composable(
            route = Screen.SignUpScreen.route
        ) {
            SignUpScreen(navController)

        }
        composable(
            route = Screen.LogInScreen.route
        ) {
            LogInScreen(navController)

        }
        composable(
            route = Screen.ForgetPasswardScreen.route
        ) {
            ForgetPasswardScreen(navController)

        }
        composable(
            route = Screen.WellcomeScreen.route
        ) {
            WellcomeScreen(navController)

        }
        composable(
            route = Screen.LoginPageScreen.route
        ) {
            LoginPageScreen(navController)

        }
        composable(
            route = Screen.PredictResultScreen.route
        ){
            PredictResultScreen(navController)
        }
        composable(
            route = Screen.AddWorkSpaceScreen.route
        ) {
            AddWorkSpaceScreen(navController){
                it.let { workspace->
                    localWorkSpaceViewModel.addWorkSpace(workSpace = workspace)
                    navController.navigate(Screen.WorkSpaceScreen.route)
                }
            }



        }
        composable(
            route = Screen.DetailedWorkSpaceScreen.route
        ){
            val workSpace = localWorkSpaceViewModel.currentWorkSpace.collectAsState()
            DetailedWorkSpaceScreen( modifier = Modifier,navController,workSpace.value){

            }}

        composable(
            route = Screen.WorkSpaceScreen.route,
        ){
            WorkSpaceScreenScreen( modifier = Modifier,navController){

            }
        }


    }
}