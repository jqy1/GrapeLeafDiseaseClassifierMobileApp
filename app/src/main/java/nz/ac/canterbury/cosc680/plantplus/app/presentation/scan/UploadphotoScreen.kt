package nz.ac.canterbury.cosc680.plantplus.app.presentation.scan

import CameraImagePreview
import EMPTY_IMAGE_URI
import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import nz.ac.canterbury.cosc680.plantplus.LocalPhotoViewModel
import nz.ac.canterbury.cosc680.plantplus.LocalPredictServiceViewModel
import nz.ac.canterbury.cosc680.plantplus.R
import nz.ac.canterbury.cosc680.plantplus.app.domain.Photo
import nz.ac.canterbury.cosc680.plantplus.app.domain.WorkSpace
import nz.ac.canterbury.cosc680.plantplus.app.navigation.Screen
import nz.ac.canterbury.cosc680.plantplus.app.presentation.components.TopBar
import nz.ac.canterbury.cosc680.plantplus.app.presentation.utils.Permission
import nz.ac.canterbury.cosc680.plantplus.app.presentation.utils.RealPathUtil
import nz.ac.canterbury.cosc680.plantplus.app.presentation.utils.randomStringByKotlinCollectionRandom

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun UploadPhotoScreen(
    navController: NavHostController,
    workSpace: WorkSpace = WorkSpace(name = "Test_"+randomStringByKotlinCollectionRandom(8),
        desc = "desc "+randomStringByKotlinCollectionRandom(16))
) {
    val localPredictViewModel = LocalPredictServiceViewModel.current
    val context = LocalContext.current
    val localPhotoViewModel = LocalPhotoViewModel.current

    var imageUri by remember { mutableStateOf(EMPTY_IMAGE_URI) }

    Scaffold(
        topBar = {
            TopBar(title = stringResource(id = R.string.upload), onClick = {
                navController.navigate(Screen.ScanScreen.route)
            })
        },
        //bottomBar = {
        //    ScreenBottomBar(navController)
        //}
    ) {
        UploadphotoScreenBody(navController = navController){ selectedUri->
            Log.i("UPLOAD", "${selectedUri.path},${selectedUri.query}")
            imageUri = selectedUri

        }
        if (imageUri != EMPTY_IMAGE_URI){

            CameraImagePreview(modifier = Modifier,navController, imageUri){ newUri->
                imageUri = newUri
                if(imageUri!=EMPTY_IMAGE_URI){

                    var path = RealPathUtil.getRealPath(context,imageUri)
                    var photo = path?.let { it1 -> Photo(it1,imageUri.toString()  ) }
                    if (photo != null) {
                        Log.d("UploadPhotoScreen",photo.toString())
                        photo.workSpaceId = workSpace.workSpaceID
                        localPhotoViewModel.addPhoto(photo = photo)
                        localPredictViewModel.predict(context,imageUri,workSpace,photo)

                    }

                }
            }
        }
    }
}


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun UploadphotoScreenBody(modifier: Modifier = Modifier,
                          navController: NavHostController,
                          onImageUri:(uri:Uri) -> Unit

) {

    var showGallerySelect by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val predictViewModel = LocalPredictServiceViewModel.current
    GallerySelect( modifier = modifier, onImageUri)
}

@Composable
fun LaunchGallery(modifier: Modifier = Modifier,
                  onImageUri: (Uri) -> Unit = { }) {
    val context = LocalContext.current
    val contentResolver = context.contentResolver

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            // here we need to copy the selected image to our folder, then avoid it doesn't display again when reopen the application.
            val contentValues = ContentValues()
            val inputStream = context.contentResolver.openInputStream(uri!!)
            val localImageUri = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)?.also{ localUri->
                context.contentResolver.openOutputStream(localUri).use {outputStream->
                    if (inputStream != null) {
                        if (outputStream != null) {
                            inputStream.copyTo(outputStream)
                        }
                    }
                }
            }

            onImageUri(localImageUri ?: EMPTY_IMAGE_URI)
        },
    )

    SideEffect {
        launcher.launch("image/*")
    }
}

@ExperimentalPermissionsApi
@Composable
fun GallerySelect(
    modifier: Modifier = Modifier,
    onImageUri: (Uri) -> Unit = { }
) {
    val context = LocalContext.current

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        Permission(
            permission = Manifest.permission.ACCESS_MEDIA_LOCATION,
            rationale = "You want to read from photo gallery, so I'm going to have to ask for permission.",
            permissionNotAvailableContent = {
                Column(modifier) {
                    Text("O noes! No Photo Gallery!")
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        Button(
                            modifier = Modifier.padding(4.dp),
                            onClick = {
                                context.startActivity(
                                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                        data = Uri.fromParts("package", context.packageName, null)
                                    }
                                )
                            }
                        ) {
                            Text("Open Settings")
                        }
                        // If they don't want to grant permissions, this button will result in going back
                        Button(
                            modifier = Modifier.padding(4.dp),
                            onClick = {
                                onImageUri(EMPTY_IMAGE_URI)
                            }
                        ) {
                            Text("Use Camera")
                        }
                    }
                }
            },
        ) {
            LaunchGallery(modifier,onImageUri)
        }
    } else {
        LaunchGallery(modifier,onImageUri)
    }
}