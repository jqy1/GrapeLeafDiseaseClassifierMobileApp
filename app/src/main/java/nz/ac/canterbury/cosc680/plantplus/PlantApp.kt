package nz.ac.canterbury.cosc680.plantplus

import android.app.Application
import nz.ac.canterbury.cosc680.plantplus.app.data.remote.PredictRepository
import nz.ac.canterbury.cosc680.plantplus.app.data.remote.ServicesRepository
import nz.ac.canterbury.cosc680.plantplus.app.data.source.PlantDatabase
import nz.ac.canterbury.cosc680.plantplus.app.data.source.PlantlocalRepostiory

class PlantApp : Application() {

    val database by lazy { PlantDatabase.getDatabase(this) }

    var authToken : String = ""

    var isLogin :Boolean = false

    val servicesRepository by lazy { ServicesRepository() }

    val predictRepository by lazy { PredictRepository() }

    val plantlocalRepostiory by lazy { PlantlocalRepostiory(database.userDao(), database.workspaceDao(),database.resultDao(),database.photoDao()) }

    companion object {
        lateinit var instance: PlantApp private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}