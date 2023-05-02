package nz.ac.canterbury.cosc680.plantplus.app.navigation

sealed class Screen(val route: String) {
    object MainScreen: Screen("MainScreen")
    object UserProfileScreen: Screen("UserProfileScreen")
    object SettingScreen: Screen("SettingScreen")
    object ResultScreen: Screen("ResultScreen")
    object ScanHistoryScreen: Screen("ScanHistoryScreen")
    object PlantstateScreen: Screen("PlantstateScreen")
    object StarsScreen: Screen("StarsScreen")
    object PasswardchangeScreen: Screen("PasswardchangeScreen")
    object PersonalinfoScreen: Screen("PersonalinfoScreen")
    object ScanScreen: Screen("ScanScreen")
    object CameraScreen: Screen("CameraScreen")
    object UploadphotoScreen: Screen("UploadphotoScreen")
    object SignUpScreen: Screen("SignUpScreen")
    object LogInScreen: Screen("LogInScreen")
    object ForgetPasswardScreen: Screen("ForgetPasswardScreen")
    object WellcomeScreen: Screen("WellcomeScreen")
    object LoginPageScreen: Screen("LoginPageScreen")
    object PredictResultScreen: Screen("PredictResultScreen")
    object AddWorkSpaceScreen: Screen("AddWorkSpaceScreen")
    object WorkSpaceScreen: Screen("WorkSpaceScreen")
    object DetailedWorkSpaceScreen: Screen("DetailedWorkSpaceScreen")


}