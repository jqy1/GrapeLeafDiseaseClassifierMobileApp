

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.core.UseCase
import androidx.camera.view.PreviewView
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import nz.ac.canterbury.cosc680.plantplus.R
import nz.ac.canterbury.cosc680.plantplus.app.navigation.Screen
import nz.ac.canterbury.cosc680.plantplus.app.presentation.components.TopBar
import nz.ac.canterbury.cosc680.plantplus.app.presentation.utils.Permission
import nz.ac.canterbury.cosc680.plantplus.app.presentation.scan.takePicture
import nz.ac.canterbury.cosc680.plantplus.app.presentation.scan.executor
import nz.ac.canterbury.cosc680.plantplus.app.presentation.scan.getCameraProvider
import coil.compose.rememberImagePainter

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import kotlinx.coroutines.launch
import nz.ac.canterbury.cosc680.plantplus.LocalPhotoViewModel
import nz.ac.canterbury.cosc680.plantplus.LocalPredictServiceViewModel
import nz.ac.canterbury.cosc680.plantplus.app.domain.Photo
import nz.ac.canterbury.cosc680.plantplus.app.domain.WorkSpace
import nz.ac.canterbury.cosc680.plantplus.app.presentation.utils.RealPathUtil

val EMPTY_IMAGE_URI: Uri = Uri.parse("file://dev/null")

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun CameraScreen(
    navController: NavHostController, workSpace:WorkSpace = WorkSpace()
) {
    val localPredictViewModel = LocalPredictServiceViewModel.current
    val localPhotoViewModel = LocalPhotoViewModel.current
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf(EMPTY_IMAGE_URI) }

    Scaffold(
        topBar = {
            TopBar(title = stringResource(id = R.string.take_photo), onClick = {
                navController.navigate(Screen.ScanScreen.route)
            })
        },
    ) {
        if (imageUri != EMPTY_IMAGE_URI){
            CameraImagePreview(modifier = Modifier,navController, imageUri){
                imageUri = it
                if(it!=EMPTY_IMAGE_URI){
                    //Save captured photo into database table, then invoke PredictViewModel's predict
                    // function to gain the classification result;

                    var path = RealPathUtil.getRealPath(context,imageUri)
                    var photo = path?.let { it1 -> Photo(it1,imageUri.toString()  ) }
                    if (photo != null) {
                        photo.workSpaceId = workSpace.workSpaceID
                        // Save Photos
                        localPhotoViewModel.addPhoto(photo = photo)
                        // Invoke predict function to get result of grapevine leaf disease classification
                        localPredictViewModel.predict(context,imageUri,workSpace, photo)
                    }
                }
            }
        }
        else{
            CameraScreenBody(navController = navController){
                imageUri = it
                Log.i("CAPTURE", "${imageUri.path},${imageUri.query}")

            }
        }
    }
}

@Composable
fun CameraImagePreview(modifier: Modifier = Modifier,navController: NavHostController,imageUri : Uri, onClick: (uri:Uri) -> Unit){

    Box(modifier = modifier) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = rememberImagePainter(imageUri),
            contentDescription = "Captured image"
        )
        Row(modifier = Modifier.align(Alignment.BottomCenter)) {
            Button(
                modifier = Modifier.padding(10.dp),
                onClick = {
                    onClick(EMPTY_IMAGE_URI)
                }
            ) {
                Text("Retake image")
            }
            Button(
                modifier = Modifier.padding(10.dp),
                onClick = {
                    onClick(imageUri)
                    navController.navigate("PredictResultScreen")
                }
            ) {
                Text("Predict")
            }
        }
    }
}


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraScreenBody(modifier: Modifier = Modifier,navController: NavHostController,
                     cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA,
                     onImageFile: (Uri) -> Unit = { }
) {
    val context = LocalContext.current
    Permission(
        permission = Manifest.permission.CAMERA,
        rationale = "You said you wanted a picture, so I'm going to have to ask for permission.",
        permissionNotAvailableContent = {
            Column(modifier) {
                Text("O noes! No Camera!")
                Spacer(modifier = Modifier.height(8.dp))
                Button(
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
            }
        }
    ){
        CameraCapture(modifier = modifier, CameraSelector.DEFAULT_BACK_CAMERA, onImageFile)
    }
}


@Composable
fun CameraCapture(modifier: Modifier = Modifier,
                  cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA,
                  onImageFile: (Uri) -> Unit = { }){
    val context = LocalContext.current

    Box(modifier = modifier) {
        val lifecycleOwner = LocalLifecycleOwner.current
        val coroutineScope = rememberCoroutineScope()
        var previewUseCase by remember { mutableStateOf<UseCase>(Preview.Builder().build()) }
        val imageCaptureUseCase by remember {
            mutableStateOf(
                ImageCapture.Builder()
                    .setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY)
                    .build()
            )
        }
        Box {
            CameraPreview(
                modifier = Modifier.fillMaxSize(),
                onUseCase = {
                    previewUseCase = it
                }
            )
            CapturePictureButton(
                modifier = Modifier
                    .size(100.dp)
                    .padding(16.dp)
                    .align(Alignment.BottomCenter),
                onClick = {
                    coroutineScope.launch {
                        imageCaptureUseCase.takePicture(context,context.executor,).let {
                            onImageFile(it)
                        }
                    }
                }
            )
        }
        LaunchedEffect(previewUseCase) {
            val cameraProvider = context.getCameraProvider()
            try {
                // Must unbind the use-cases before rebinding them.
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    lifecycleOwner, cameraSelector, previewUseCase, imageCaptureUseCase
                )
            } catch (ex: Exception) {
                Log.e("CameraCapture", "Failed to bind camera use cases", ex)
            }
        }
    }
}

@Composable
fun CameraPreview(
    modifier: Modifier = Modifier,
    scaleType: PreviewView.ScaleType = PreviewView.ScaleType.FIT_CENTER,
    lensFacing: Int = CameraSelector.LENS_FACING_BACK,
    onUseCase: (UseCase) -> Unit = { }
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            val previewView = PreviewView(context).apply {
                this.scaleType = scaleType
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
            onUseCase(
                Preview.Builder()
                    .build()
                    .also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }
            )
            previewView
        }
    )
}




@Composable
fun CapturePictureButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { },
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val color = if (isPressed) Color.Blue else Color.Black
    val contentPadding = PaddingValues(if (isPressed) 8.dp else 12.dp)
    OutlinedButton(
        modifier = modifier,
        shape = CircleShape,
        border = BorderStroke(2.dp, Color.Black),
        contentPadding = contentPadding,
        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black),
        onClick = { /* GNDN */ },
        enabled = false
    ) {
        Button(
            modifier = Modifier.fillMaxSize(),
            shape = CircleShape,
            interactionSource = interactionSource,
            onClick = onClick
        ) {
            // No content
        }
    }
}
