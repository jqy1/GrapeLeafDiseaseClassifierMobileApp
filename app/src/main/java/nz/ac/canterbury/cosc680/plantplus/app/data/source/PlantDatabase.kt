package nz.ac.canterbury.cosc680.plantplus.app.data.source

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import nz.ac.canterbury.cosc680.plantplus.app.data.source.local.PhotoDao
import nz.ac.canterbury.cosc680.plantplus.app.data.source.local.ResultDao
import nz.ac.canterbury.cosc680.plantplus.app.data.source.local.UserDao
import nz.ac.canterbury.cosc680.plantplus.app.data.source.local.WorkSpaceDao
import nz.ac.canterbury.cosc680.plantplus.app.domain.Photo
import nz.ac.canterbury.cosc680.plantplus.app.domain.PredictResult
import nz.ac.canterbury.cosc680.plantplus.app.domain.User
import nz.ac.canterbury.cosc680.plantplus.app.domain.WorkSpace

// create database class
@Database(
    entities = [ User::class, Photo::class, WorkSpace::class, PredictResult::class ],
    version = 1,
    exportSchema = false
)
abstract class PlantDatabase : RoomDatabase() {

    abstract fun userDao() : UserDao

    abstract fun photoDao() : PhotoDao

    abstract fun resultDao() : ResultDao

    abstract fun workspaceDao() : WorkSpaceDao

    companion object{
        @Volatile
        private var INSTANCE: PlantDatabase? = null
        fun getDatabase(context: Context): PlantDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PlantDatabase::class.java,
                    "plant_database"
                ).apply {
                    //addMigrations(MIGRATION_4_5)
                }.allowMainThreadQueries().build()// .allowMainThreadQueries() -- 在操作Room时闪退 - 在Room.databaseBuilder时开启运行主线程
                //).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}