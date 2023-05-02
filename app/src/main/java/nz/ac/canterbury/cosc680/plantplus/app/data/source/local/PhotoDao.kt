package nz.ac.canterbury.cosc680.plantplus.app.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow
import nz.ac.canterbury.cosc680.plantplus.app.domain.Photo

@Dao
interface PhotoDao {
    @Query("SELECT * FROM photos")
    fun getAll(): List<Photo>

    @Query("SELECT * FROM photos")
    fun loadAll(): Flow<List<Photo>>

    @Query("SELECT * FROM photos WHERE workSpaceId in (:workSpaceId) ")
    fun getPhotosByWorkSpaceID(workSpaceId:Long = 0 ): LiveData<List<Photo>>

    @Query("SELECT * FROM photos WHERE workSpaceId in (:workSpaceId) limit 1")
    fun getOnePhotosByWorkSpaceID(workSpaceId:Long = 0 ): LiveData<Photo>

    @Insert
    fun insert(photo : Photo): Long

    @Update
    fun update(photo: Photo)

    @Delete
    fun delete(photo: Photo)

}