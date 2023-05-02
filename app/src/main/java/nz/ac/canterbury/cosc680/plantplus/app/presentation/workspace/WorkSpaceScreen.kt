package nz.ac.canterbury.cosc680.plantplus.app.presentation.workspace

import android.annotation.SuppressLint
import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import nz.ac.canterbury.cosc680.plantplus.*
import nz.ac.canterbury.cosc680.plantplus.R
import nz.ac.canterbury.cosc680.plantplus.app.domain.Photo
import nz.ac.canterbury.cosc680.plantplus.app.domain.WorkSpace
import nz.ac.canterbury.cosc680.plantplus.app.navigation.Screen
import nz.ac.canterbury.cosc680.plantplus.app.presentation.components.ScreenBottomBar
import nz.ac.canterbury.cosc680.plantplus.app.presentation.components.SmallUsableButton
import nz.ac.canterbury.cosc680.plantplus.app.presentation.components.TopBar
import nz.ac.canterbury.cosc680.plantplus.app.ui.theme.PlantPlusTheme.PlantPlusTheme

private val imageData = listOf(
    R.drawable.test1 to R.string.test1,
    R.drawable.test1 to R.string.test2,
    R.drawable.test1 to R.string.test3,
    R.drawable.test1 to R.string.test4,
    R.drawable.test1 to R.string.test4,
    R.drawable.test1 to R.string.test4,
).map { DrawableStringPair(it.first, it.second) }

private data class DrawableStringPair(
    @DrawableRes val drawable: Int,
    @StringRes val text: Int
)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun WorkSpaceScreenScreen(
    modifier: Modifier = Modifier,navController: NavController = rememberNavController(),
    onClickCreate : (workSpace: WorkSpace)-> Unit
) {

    val localWorkSpaceViewModel = LocalWorkSpackeViewModel.current
    val allworkSpaces = localWorkSpaceViewModel.allWorkSpace.observeAsState()

    Scaffold(
        topBar = {
            TopBar(stringResource(R.string.workspace1), isMainScreen = true, onClick = {})
            SmallUsableButton(text = stringResource(id = R.string.add), onClick = {
                navController.navigate(Screen.AddWorkSpaceScreen.route)
            })
        },
        bottomBar = {ScreenBottomBar(navController)},
        ){ itPaddingValue ->
        LazyColumn(modifier = Modifier
            .padding(itPaddingValue)
            .fillMaxWidth()) {
                allworkSpaces.value?.let {list->
                    items(list){ workSpaceItem->
                        CardWorkSpace(modifier = modifier, workSpace = workSpaceItem) {
                            localWorkSpaceViewModel.onSetCurrentWorkSpace(workSpaceItem)
                            navController.navigate(Screen.DetailedWorkSpaceScreen.route)
                        }
                    }
                }
            }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun CardWorkSpace(
    modifier: Modifier = Modifier,workSpace: WorkSpace, onClick: ()->Unit
) {
    Card(modifier = Modifier.padding(8.dp)) {
        Column(modifier = Modifier.padding(vertical = 16.dp),) {
            CardHeader( modifier = Modifier
                .padding(horizontal = 16.dp)
                .clickable {
                    onClick()
                }, R.drawable.test1, workSpace){

            }
            WorkSpaceBodyRow(modifier = Modifier.padding(top = 8.dp),workSpace=workSpace)
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun CardHeader(modifier: Modifier = Modifier,
               @DrawableRes drawable: Int,
               workSpace: WorkSpace, onClick: ()->Unit){

    val localPhotoViewModel = LocalPhotoViewModel.current
    val photo = localPhotoViewModel.pickOnePhotoByWorkSpaceId(workSpace.workSpaceID).observeAsState()


    // Implement composable here
    Surface(
        shape = MaterialTheme.shapes.small,
        modifier = modifier
    ) {
        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            photo.value?.let {
                val imageUri = Uri.parse(it.uri)
                Image(
                    painter = rememberImagePainter(imageUri),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(56.dp))
            }


            Column(modifier = Modifier.padding(8.dp),) {
                androidx.compose.material.Text(
                    text = workSpace.name,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                androidx.compose.material.Text(
                    text = workSpace.desc,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
}

// Step: Align your body row - Arrangements
@Composable
fun WorkSpaceBodyRow(
    modifier: Modifier = Modifier, workSpace: WorkSpace = WorkSpace("name","desc")
) {
    val localPhotoViewModel = LocalPhotoViewModel.current

    var allPhotos = localPhotoViewModel.getWorkSpacePhotos(workSpace.workSpaceID).observeAsState()

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = modifier
    ) {
        allPhotos.value?.let {photos->
            items(photos) { item ->
                WorkSpaceImage(item.uri, item.path){

                }
            }
        }

    }
}


// Step: Align your body - Alignment
@Composable
fun WorkSpaceNotPhotos(
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "No photos yet!",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

    }
}


// Step: Align your body - Alignment
@Composable
fun WorkSpaceImagePreview(
    @DrawableRes drawable: Int,
    @StringRes text: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(drawable),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(88.dp)
                .clip(RectangleShape)
        )

    }
}

@Composable
fun WorkSpaceImage(
    imagePath: String,
    uri: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val imageUri = Uri.parse(imagePath)
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = rememberImagePainter(imageUri),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(88.dp)
                .clip(RectangleShape)
        )

    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun WorkSpaceDefaultPreview() {
    var workSpace = WorkSpace("WB1","Uninversity of Canterbury")
    PlantPlusTheme {
        Card(modifier = Modifier.padding(8.dp)) {
            Column(modifier = Modifier.padding(vertical = 16.dp),) {
                CardHeader( modifier = Modifier.padding(horizontal = 16.dp),
                    R.drawable.test1, workSpace){

                }
                WorkSpaceBodyRow(modifier = Modifier.padding(top = 8.dp))
            }
        }
    }
}