package nz.ac.canterbury.cosc680.plantplus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import nz.ac.canterbury.cosc680.plantplus.app.di.*
import nz.ac.canterbury.cosc680.plantplus.app.navigation.NavGraph
import nz.ac.canterbury.cosc680.plantplus.app.presentation.Popouplogout
import nz.ac.canterbury.cosc680.plantplus.app.presentation.utils.ShowPredictJSON
import nz.ac.canterbury.cosc680.plantplus.app.ui.theme.PlantPlusTheme.PlantPlusTheme


// Define a CompositionLocal global object with a default
// This instance can be accessed by all composables in the app
val LocalServiceViewModel = compositionLocalOf { UserServiceViewModel( PlantApp.instance.servicesRepository, PlantApp.instance.plantlocalRepostiory ) }
val LocalPredictServiceViewModel = compositionLocalOf { PredictServiceViewModel( PlantApp.instance.predictRepository, PlantApp.instance.plantlocalRepostiory ) }
val LocalWorkSpackeViewModel = compositionLocalOf { WorkSpaceViewModel(PlantApp.instance.plantlocalRepostiory ) }
val LocalPhotoViewModel = compositionLocalOf { PhotoViewModel(PlantApp.instance.plantlocalRepostiory ) }

@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    // create viewmodel
    private val userServiceViewModel : UserServiceViewModel by viewModels{
        val plantApp = (this?.application as PlantApp)
        UserServiceViewModelFactory( plantApp.servicesRepository,plantApp.plantlocalRepostiory )
    }

    private val predictServiceViewModel : PredictServiceViewModel by viewModels{
        val plantApp = (this?.application as PlantApp)
        PredictServiceViewModelFactory( plantApp.predictRepository,plantApp.plantlocalRepostiory)
    }

    private val workSpaceViewModel : WorkSpaceViewModel by viewModels{
        val plantApp = (this?.application as PlantApp)
        WorkSpaceViewModelFactory( plantApp.plantlocalRepostiory)
    }

    private val photoViewModel : PhotoViewModel by viewModels{
        val plantApp = (this?.application as PlantApp)
        PhotoViewModelViewModelFactory( plantApp.plantlocalRepostiory)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PlantPlusTheme {
                // Bind elevation as the value for LocalElevations
                val navController = rememberNavController()
                CompositionLocalProvider(
                    LocalServiceViewModel provides userServiceViewModel,
                    LocalPredictServiceViewModel provides predictServiceViewModel,
                    LocalWorkSpackeViewModel provides  workSpaceViewModel,
                    LocalPhotoViewModel provides  photoViewModel,
                ) {
                    ShowPredictJSON()
                    NavGraph(navController = navController)
                    Popouplogout(text = "", onClick = { /*TODO*/ }, navController = navController )
                }
            }


        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PlantPlusTheme {
        NavGraph(navController = rememberNavController())
    }
}