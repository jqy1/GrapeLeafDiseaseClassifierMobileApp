package nz.ac.canterbury.cosc680.plantplus.app.data.remote


import android.util.Log
import androidx.annotation.WorkerThread
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import nz.ac.canterbury.cosc680.plantplus.PlantApp
import nz.ac.canterbury.cosc680.plantplus.app.domain.User
import nz.ac.canterbury.cosc680.plantplus.app.utils.SSLAgent
import org.json.JSONObject
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.URL
import java.nio.charset.Charset
import java.time.LocalDateTime
import java.util.*
import javax.net.ssl.HttpsURLConnection


class ServicesRepository() {
    var reponseLoginParser = ResponseLoginParser()
    var resposeRegisterParser = ResponRegisterParser()

    class ResponRegisterParser()  {
        fun parse(inputStream: InputStream, connection: HttpsURLConnection): User {
            val json = BufferedInputStream(inputStream).readBytes()
                .toString(Charset.defaultCharset())
            Log.d("ResponseLoginParser/inputStream",json )
            when (connection.responseCode) {
                200 -> {
                    var loginJSONObject = JSONObject(json)

                    (loginJSONObject.get("data") as JSONObject).get("Authorization")?.let {
                        PlantApp.instance.authToken = it.toString()
                        Log.d("D/Auth_token",PlantApp.instance.authToken )
                    }
                }
                401 -> {

                }
                else -> { // Note the block

                }
            }
            return User()
        }
    }

    class ResponseLoginParser()  {
        fun parse(inputStream: InputStream, connection: HttpsURLConnection): User {
            val json = BufferedInputStream(inputStream).readBytes()
                .toString(Charset.defaultCharset())
            Log.d("ResponseLoginParser/inputStream",json )
            when (connection.responseCode) {
                200 -> {
                    var loginJSONObject = JSONObject(json)

                    (loginJSONObject.get("data") as JSONObject).get("Authorization")?.let {
                        PlantApp.instance.authToken = it.toString()
                        Log.d("D/Auth_token",PlantApp.instance.authToken )
                    }
                }
                401 -> {

                }
                else -> { // Note the block

                }
            }
            return User()
        }
    }

    /*
    * TODO: need to cheeck Suppress and WorkThread Annotation here, need to handle network error
    *
    * @Suppress closes the compiled warring information
    * @WorkerThread checks the invoker whether is a work thread.
    *  */
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun login(userName:String = "", password:String = ""): Result<Response<User>> {

        SSLAgent.instance?.trustAllHttpsCertificates()

        /* Use the withContext() function from the coroutines library to move the execution of a coroutine to a different thread:*/
        return withContext(Dispatchers.IO){
            var currentUser = User()
            var code = 400
            try {
                var jsonBody = "{\"email\": \"$userName\",\"password\":\"$password\"}"
                val url = URL(BackendApiRouter.LOGIN)

                (url.openConnection() as? HttpsURLConnection)?.run {

                    requestMethod = "POST"
                    setRequestProperty("Content-Type", "application/json")
                    setRequestProperty("Accept", "*/*")
                    doOutput = true
                    outputStream.write(jsonBody.toByteArray())

                    reponseLoginParser.parse(inputStream, this)
                    code = this.responseCode
                }
            }catch(e:IOException){
                Result.Error(Exception(e.message))
            }
            Result.Success( Response<User>(true,"okay", code = code, t = currentUser) )
        }
    }


    /*
    * TODO: need to cheeck Suppress and WorkThread Annotation here, need to handle network error
    *
    * @Suppress closes the compiled warring information
    * @WorkerThread checks the invoker whether is a work thread.
    *  */
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun register(firstname:String = "", surname:String = "",email:String = "",userName:String = "", password:String = "", ): Result<Response<User>> {

        SSLAgent.instance?.trustAllHttpsCertificates()

        /* Use the withContext() function from the coroutines library to move the execution of a coroutine to a different thread:*/
        return withContext(Dispatchers.IO){
            var currentUser = User()
            var code = 400
            try {
                var jsonBody = "{\n" +
                        "  \"first_name\": \"$firstname\",\n" +
                        "  \"sur_name\": \"$surname\",\n" +
                        "  \"mail\": \"$email\",\n" +
                        "  \"password_hash\": \"$password\",\n" +
                        "  \"registered_on\": \"\",\n" +
                        "  \"admin\": \"1\",\n" +
                        "  \"public_id\": \"\",\n" +
                        "  \"user_level\": \"normal9\",\n" +
                        "  \"user_name\": \"$userName\"\n" +
                        "}"
                val url = URL(BackendApiRouter.CREATE)

                (url.openConnection() as? HttpsURLConnection)?.run {

                    requestMethod = "POST"
                    setRequestProperty("Content-Type", "application/json")
                    setRequestProperty("Accept", "*/*")
                    doOutput = true
                    outputStream.write(jsonBody.toByteArray())

                    resposeRegisterParser.parse(inputStream, this)
                    code = this.responseCode

                }
            }catch(e:IOException){
                Result.Error(Exception(e.message))
            }
            Result.Success( Response<User>(true,"okay", code = code, t = currentUser) )
            //Result.Error(Exception("Cannot open HttpURLConnection"))
        }
    }
}