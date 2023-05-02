package nz.ac.canterbury.cosc680.plantplus.app.data.source.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import nz.ac.canterbury.cosc680.plantplus.app.domain.PredictResult

@Dao
interface ResultDao {

    @Query("SELECT * FROM results")
    fun getAll(): List<PredictResult>

    @Query("SELECT * FROM results")
    fun loadAll(): Flow<List<PredictResult>>

    @Query("SELECT * FROM results where photoId=(:photoId)")
    fun getResultByPhotoId(photoId:Long): Flow<List<PredictResult>>

    @Query("SELECT * FROM results where photoId=(:photoId) order by possibility desc")
    fun getResultByPhotoIdList(photoId:Long): List<PredictResult>

    @Insert
    fun insert(result : PredictResult): Long

    @Update
    fun update(result: PredictResult)

    @Delete
    fun delete(result: PredictResult)

}