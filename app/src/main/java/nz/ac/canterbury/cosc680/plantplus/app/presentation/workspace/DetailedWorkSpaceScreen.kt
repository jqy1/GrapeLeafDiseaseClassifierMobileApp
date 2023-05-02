package nz.ac.canterbury.cosc680.plantplus.app.presentation.workspace


import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.TopAppBar
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource

import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import nz.ac.canterbury.cosc680.plantplus.LocalPhotoViewModel
import nz.ac.canterbury.cosc680.plantplus.LocalPredictServiceViewModel
import nz.ac.canterbury.cosc680.plantplus.PlantApp
import nz.ac.canterbury.cosc680.plantplus.app.domain.WorkSpace
import nz.ac.canterbury.cosc680.plantplus.app.presentation.components.ScreenBottomBar
import nz.ac.canterbury.cosc680.plantplus.app.presentation.components.TopBar
import java.net.URLDecoder
import nz.ac.canterbury.cosc680.plantplus.R
import nz.ac.canterbury.cosc680.plantplus.app.di.PredictResultViewModel
import nz.ac.canterbury.cosc680.plantplus.app.domain.Photo
import nz.ac.canterbury.cosc680.plantplus.app.navigation.Screen


@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun DetailedWorkSpaceScreen(
    modifier: Modifier = Modifier,navController: NavController, workSpace: WorkSpace = WorkSpace
        (),predictResultViewModel: PredictResultViewModel = viewModel(),onClickCreate : (workSpace: WorkSpace)-> Unit
) {
    val localPhotoViewModel = LocalPhotoViewModel.current
    val context = LocalContext.current

    var allPhotos = localPhotoViewModel.getWorkSpacePhotos(workSpace.workSpaceID).observeAsState()
    Scaffold(
        topBar = { TopBar((workSpace.name), isMainScreen = true, onClick = {})},
        bottomBar = {ScreenBottomBar(navController)})
    {paddingValue->
        Box(modifier = Modifier.padding(paddingValue)){

            LazyColumn(modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth() ){
                allPhotos.value?.let {photos->
                    items(photos){
                        Log.d("DetailedWorkSpaceScreen",it.toString())
                        WorkSpaceImageDetail( navController = navController, photo = it, workSpace = workSpace ){clickWorkSpace->
                            onClickCreate( clickWorkSpace )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkSpaceImageDetail(
    modifier: Modifier = Modifier,navController: NavController,  workSpace: WorkSpace, photo: Photo, onClick: (workSpace:WorkSpace) -> Unit
) {


    val imageRawPath = URLDecoder.decode(photo.uri)
    val imageUri = Uri.parse(  imageRawPath )

    val predictResultViewModel: PredictResultViewModel = viewModel()
    predictResultViewModel.setWorkSpaceAndPhotoId(photo,workSpace)

    var predictResults = predictResultViewModel.predictResults.observeAsState()
    val predictlocalViewModel = LocalPredictServiceViewModel.current


    Log.d("WorkSpaceImageDetail","$photo")
    Card(modifier = modifier
        .padding(8.dp)
        .height(196.dp), shape = MaterialTheme.shapes.medium,
         colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ){

            Row(modifier = modifier.fillMaxHeight(),verticalAlignment = Alignment.CenterVertically,horizontalArrangement = Arrangement.End){
                Image(
                    painter = if (imageUri != null) {
                        rememberAsyncImagePainter(
                            ImageRequest.Builder(LocalContext.current).data(data = imageUri).apply(block = fun ImageRequest.Builder.() {
                                placeholder(0)
                            }).build(),
                        )
                    } else {
                        painterResource(id = R.drawable.test)
                    },
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(196.dp)
                        .fillMaxHeight()
                        .clip(RectangleShape)
                )

                Column(
                    modifier = modifier.fillMaxSize(  ),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Log.d("WorkSpaceImageDetail", predictResults.value.toString())


                    predictResults.value?.let { predictResults1 ->
                        predictResults1.forEach(){
                            Log.d("Result",it.toString())
                        }
                        if (predictResults1.isNotEmpty())
                            Text(text = predictResults1[0].disease  + "-" + predictResults1[0].possibility.toString() , modifier = modifier.padding(top = 16.dp, start = 16.dp),  style = MaterialTheme.typography.headlineSmall, )
                    }

                    //Text(text = predictResults.value[0].disease + predictResults.value[0].possibility.toString() , modifier = modifier.padding(top = 16.dp, start = 16.dp),  style = MaterialTheme.typography.headlineSmall, )
                    Row(modifier = modifier
                        .fillMaxWidth()
                        .weight(1f, false),horizontalArrangement = Arrangement.End,
                        ){
                        TextButton(modifier = modifier.padding(8.dp),
                            colors =ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            ), onClick = {
                                //if the results is not empty, then we can navigate to predict result screen.
                                predictResults.value?.let {results->
                                    if(results.isNotEmpty()) {
                                        predictlocalViewModel.setPredictResults(results)
                                        navController.navigate(Screen.ResultScreen.route)
                                    }
                                }
                            }){
                            Text(text = stringResource(id = R.string.predict))
                        }

                        TextButton(modifier =modifier.padding(8.dp),
                            colors =ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondary,
                                contentColor = MaterialTheme.colorScheme.onSecondary
                            ),
                            onClick = {
                                //navController.navigate(Screen.AddWorkSpaceScreen.route)
                            }){
                            Text(text = stringResource(id = R.string.delete))
                        }
                    }
                }


            }
        }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun DetailedWorkSpace(
    modifier: Modifier = Modifier,workSpace: WorkSpace, onClick: ()->Unit
) {
    TopAppBar(modifier = modifier
        .fillMaxWidth()
        .padding(15.dp)
        .clickable { onClick() }) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(modifier=modifier){
                Text(workSpace.name, modifier=modifier)
                Text(workSpace.desc, modifier=modifier)
            }
        }
    }
}