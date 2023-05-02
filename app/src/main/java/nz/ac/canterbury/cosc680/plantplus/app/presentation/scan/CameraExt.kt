package nz.ac.canterbury.cosc680.plantplus.app.presentation.scan

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.location.Location
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.exifinterface.media.ExifInterface
import java.io.File
import java.util.concurrent.Executor
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import nz.ac.canterbury.cosc680.plantplus.app.presentation.utils.RealPathUtil
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

suspend fun Context.getCameraProvider(): ProcessCameraProvider = suspendCoroutine { continuation ->
    ProcessCameraProvider.getInstance(this).also { future ->
        future.addListener(
            {
                continuation.resume(future.get())
            },
            executor
        )
    }
}

val Context.executor: Executor
    get() = ContextCompat.getMainExecutor(this)

const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"

suspend fun ImageCapture.takePicture(
    context: Context, executor: Executor,
    lensFacing:Int = CameraSelector.LENS_FACING_BACK,): Uri {

    val outputOptions = getOutputFileOptions(context,lensFacing)
    return suspendCoroutine { continuation ->

        takePicture(
            outputOptions, executor,
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(output: ImageCapture.OutputFileResults) {

                    output.savedUri?.let { uri ->
                        // Write EXIF location to output file
                        Log.i("FILE","${uri.path}, ${uri.query}")
                        continuation.resume(uri)
                    }

                }

                override fun onError(ex: ImageCaptureException) {
                    Log.e("TakePicture", "Image capture failed", ex)
                    continuation.resumeWithException(ex)
                }
            }
        )
    }
}

fun getOutputFileOptions(context: Context, lensFacing: Int): ImageCapture.OutputFileOptions {
    // Setup image capture metadata

    val metadata = ImageCapture.Metadata()
    metadata.isReversedHorizontal = lensFacing == CameraSelector.LENS_FACING_FRONT

    val name = "Plant-" + SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(System.currentTimeMillis())
    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, name)
        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
        put(MediaStore.MediaColumns.RELATIVE_PATH, "${Environment.DIRECTORY_PICTURES}/Plants")
    }
    // Create output options object which contains file + metadata
    return ImageCapture.OutputFileOptions.Builder(
        context.contentResolver, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues
    ).setMetadata(metadata).build()

}


fun addLocationToFileForResult(context: Context, uri: Uri, location: Location?): Boolean {
    val file = File(RealPathUtil.getRealPath(context, uri)!!)
    try {
        val exif = ExifInterface(file)
        exif.setGpsInfo(location)
        exif.saveAttributes()

        return ExifInterface(file).let {
            !it.getAttribute(ExifInterface.TAG_GPS_LATITUDE)
                .isNullOrBlank() || !it.getAttribute(ExifInterface.TAG_GPS_LONGITUDE)
                .isNullOrBlank()
        }
    } catch (e: IOException) {
        e.printStackTrace()
        return false
    }
}
