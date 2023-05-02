package nz.ac.canterbury.cosc680.plantplus.app.data.remote

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.annotation.WorkerThread
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import nz.ac.canterbury.cosc680.plantplus.PlantApp
import nz.ac.canterbury.cosc680.plantplus.app.domain.Photo
import nz.ac.canterbury.cosc680.plantplus.app.domain.PredictResult
import nz.ac.canterbury.cosc680.plantplus.app.domain.User
import nz.ac.canterbury.cosc680.plantplus.app.domain.WorkSpace
import nz.ac.canterbury.cosc680.plantplus.app.presentation.utils.RealPathUtil
import nz.ac.canterbury.cosc680.plantplus.app.utils.SSLAgent
import org.json.JSONObject
import org.json.JSONArray
import java.io.BufferedInputStream
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.net.URL
import java.nio.charset.Charset
import java.util.*
import javax.net.ssl.HttpsURLConnection


class PredictRepository() {
    var reponseParser = ResponsePredictParser()
    var reponseFeedbackParser = ResponseFeedbackParser()


    class ResponsePredictParser()  {
        fun getNamePossibility( name:String, jsonNode:JSONObject ):Double{
            jsonNode?.let {
                if (it.has(name)) {
                    return it.getString(name).toDouble()
                }
                return 0.0
            }
            return 0.0
        }
        fun parse(inputStream: InputStream, connection: HttpsURLConnection): List<PredictResult> {
            var result = emptyList<PredictResult>()
            when (connection.responseCode) {
                200 -> {
                    val json = BufferedInputStream(inputStream).readBytes()
                        .toString(Charset.defaultCharset())
                    var loginJSONObject = JSONObject(json)
                    Log.i("NET",loginJSONObject.toString())
                    //result.disease = json
                    var newResult = PredictResult()
                    val jsonArray = loginJSONObject.getJSONArray("data")

                    for(i in 0 until jsonArray.length()) {
                        val jsonNode = jsonArray.getJSONObject(i)
                        jsonNode?.let {
                            it.keys().forEach { key->
                                if (key != "Classified Type"){
                                    var newResult = PredictResult()
                                    newResult.disease = key
                                    newResult.possibility = getNamePossibility(key,it)
                                    result += (newResult)
                                }

                            }
                        }
                    }
                }
                201 -> {

                }
                else -> { // Note the block

                }
            }
            return result
        }
    }


    class ResponseFeedbackParser()  {
        fun parse(inputStream: InputStream, connection: HttpsURLConnection): String {
            val json = BufferedInputStream(inputStream).readBytes()
                .toString(Charset.defaultCharset())
            Log.d("ResponseLoginParser/inputStream",json )
            when (connection.responseCode) {
                200 -> {
                    var loginJSONObject = JSONObject(json)

                }
                401 -> {

                }
                else -> { // Note the block

                }
            }
            return ""
        }
    }



    private val POST_FILE_BOUNDARY = "*****"
    private val CRLF = "\r\n"
    private val TWO_HYPHENS = "--"

    /*
    * TODO: need to cheeck Suppress and WorkThread Annotation here, need to handle network error
    *
    * @Suppress closes the compiled warring information
    * @WorkerThread checks the invoker whether is a work thread.
    *  */
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun predict(context: Context, uri : Uri ): Result< Response< List<PredictResult> > > {

        SSLAgent.instance?.trustAllHttpsCertificates()

        /* Use the withContext() function from the coroutines library to move the execution of a coroutine to a different thread:*/
        return withContext(Dispatchers.IO){
            var currentResult = emptyList<PredictResult>()
            try {

                val file = File(RealPathUtil.getRealPath(context, uri)!!)
                val fileStream = context.contentResolver.openInputStream(uri)
                val byteTosend = fileStream?.readBytes()

                val url = URL(BackendApiRouter.PREDICT)

                (url.openConnection() as? HttpsURLConnection)?.run {

                    requestMethod = "POST"
                    //setRequestProperty("Content-Type", "application/json")
                    setRequestProperty("Authorization",PlantApp.instance.authToken)
                    setRequestProperty("Accept", "*/*")
                    setRequestProperty("Connection", "Keep-Alive")
                    setRequestProperty("Cache-Control", "no-cache")
                    setRequestProperty( "Content-Type",
                        "multipart/form-data;boundary=$POST_FILE_BOUNDARY"
                    );
                    doOutput = true

                    outputStream.write( (TWO_HYPHENS + POST_FILE_BOUNDARY + CRLF).toByteArray());

                    outputStream.write( ("Content-Disposition: form-data; name=\"file\"; filename=\""+ file.name + "\""+ CRLF).toByteArray());
                    outputStream.write( CRLF.toByteArray())
                    outputStream.write( byteTosend );
                    outputStream.write( CRLF.toByteArray())

                    outputStream.write((TWO_HYPHENS + POST_FILE_BOUNDARY + TWO_HYPHENS + CRLF).toByteArray());
                    outputStream.flush();

                    currentResult = reponseParser.parse(inputStream, this)
                    currentResult.forEach(){
                        it.imageUri = uri
                    }
                }
            }catch(e:IOException){
                Result.Error(Exception(e.message))
            }
            Result.Success( Response< List<PredictResult> >(true,"okay", code = 200, t = currentResult) )
            //Result.Error(Exception("Cannot open HttpURLConnection"))
        }
    }


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun feedback(context: Context, uri : Uri, disease: String): Result< Response< String > > {

        SSLAgent.instance?.trustAllHttpsCertificates()

        /* Use the withContext() function from the coroutines library to move the execution of a coroutine to a different thread:*/
        return withContext(Dispatchers.IO){
            var currentResult = ""
            try {

                val file = File(RealPathUtil.getRealPath(context, uri)!!)
                val fileStream = context.contentResolver.openInputStream(uri)
                val byteTosend = fileStream?.readBytes()

                val url = URL(BackendApiRouter.FEEDBACK)

                (url.openConnection() as? HttpsURLConnection)?.run {

                    requestMethod = "POST"
                    //setRequestProperty("Content-Type", "application/json")
                    setRequestProperty("Authorization",PlantApp.instance.authToken)
                    setRequestProperty("Accept", "*/*")
                    setRequestProperty("Connection", "Keep-Alive")
                    setRequestProperty("Cache-Control", "no-cache")
                    setRequestProperty( "Content-Type",
                        "multipart/form-data;boundary=$POST_FILE_BOUNDARY"
                    );
                    doOutput = true

                    outputStream.write( (TWO_HYPHENS + POST_FILE_BOUNDARY + CRLF).toByteArray());

                    outputStream.write( ("Content-Disposition: form-data; name=\"file\"; filename=\""+ disease+"_"+ file.name + "\""+ CRLF).toByteArray());
                    outputStream.write( CRLF.toByteArray())
                    outputStream.write( byteTosend );
                    outputStream.write( CRLF.toByteArray())

                    outputStream.write((TWO_HYPHENS + POST_FILE_BOUNDARY + TWO_HYPHENS + CRLF).toByteArray());
                    outputStream.flush();

                    currentResult = reponseFeedbackParser.parse(inputStream, this)

                }
            }catch(e:IOException){
                Result.Error(Exception(e.message))
            }
            Result.Success( Response<String >(true,"okay", code = 200, t = "") )
            //Result.Error(Exception("Cannot open HttpURLConnection"))
        }
    }
}