package nz.ac.canterbury.cosc680.plantplus.app.data.remote

import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.Serializable
import nz.ac.canterbury.cosc680.plantplus.PlantApp
import nz.ac.canterbury.cosc680.plantplus.app.domain.User

@Serializable
data class RequestModel(
    val code: Int,
    val description: String,
    val data: String
)

@Serializable
data class ResponseModel(
    val code: Int,
    val description: String,
    val data: String
)


interface PlantApiService {

    suspend fun login(): User

    suspend fun createUser(userRequest: RequestModel): ResponseModel?

    companion object {
        private const val AUTHORIZATION_HEADER = "Authorization"
        fun create(): PlantApiService {
            return PlantApiServiceImpl(
                client = HttpClient(Android) {
                    // Logging
                    install(Logging) {
                        level = LogLevel.ALL
                    }
                    // JSON
                    install(JsonFeature) {
                        serializer = KotlinxSerializer(
                            kotlinx.serialization.json.Json {
                                isLenient = true
                                ignoreUnknownKeys = true
                                explicitNulls = false
                            })
                        //or serializer = KotlinxSerializer()
                    }
                    // Timeout
                    install(HttpTimeout) {
                        requestTimeoutMillis = 15000L
                        connectTimeoutMillis = 15000L
                        socketTimeoutMillis = 15000L
                    }
                    // Apply to all requests
                    defaultRequest {
                        header(AUTHORIZATION_HEADER, PlantApp.instance.authToken)
                        if (method != HttpMethod.Get) contentType(ContentType.Application.Json)
                        accept(ContentType.Application.Json)
                    }
                }
            )
        }

        private val json = kotlinx.serialization.json.Json {
            ignoreUnknownKeys = true
            isLenient = true
            encodeDefaults = false
        }
    }
}

class PlantApiServiceImpl(
    private val client: HttpClient
) : PlantApiService {

    override suspend fun login(): User {
        var user = User()
        return try {
            client.post { url(BackendApiRouter.LOGIN) }
        } catch (ex: RedirectResponseException) {
            // 3xx - responses
            println("Error: ${ex.response.status.description}")
            user
        } catch (ex: ClientRequestException) {
            // 4xx - responses
            println("Error: ${ex.response.status.description}")
            user
        } catch (ex: ServerResponseException) {
            // 5xx - response
            println("Error: ${ex.response.status.description}")
            user
        }
    }

    override suspend fun createUser(userRequest: RequestModel): ResponseModel? {
        return try {

            client.post<ResponseModel> {
                url(BackendApiRouter.CREATE)
                body = userRequest
            }
        } catch (ex: RedirectResponseException) {
            // 3xx - responses
            println("Error: ${ex.response.status.description}")
            null
        } catch (ex: ClientRequestException) {
            // 4xx - responses
            println("Error: ${ex.response.status.description}")
            null
        } catch (ex: ServerResponseException) {
            // 5xx - response
            println("Error: ${ex.response.status.description}")
            null
        }
    }
}

