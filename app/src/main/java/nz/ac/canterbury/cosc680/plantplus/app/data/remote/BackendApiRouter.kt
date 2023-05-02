package nz.ac.canterbury.cosc680.plantplus.app.data.remote

object BackendApiRouter {
    private const val BASE_URL = "https://192.168.88.37:8080"

    const val LOGIN = "$BASE_URL/api/v1/auth/login"
    const val LOGOUT = "$BASE_URL/api/v1/auth/logout"
    const val CREATE = "$BASE_URL/api/v1/user/"
    const val PREDICT = "$BASE_URL/api/v1/predict/"
    const val FEEDBACK = "$BASE_URL/api/v1/feedback/"
}
