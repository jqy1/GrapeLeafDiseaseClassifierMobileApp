package nz.ac.canterbury.cosc680.plantplus.app.data.source

import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import nz.ac.canterbury.cosc680.plantplus.app.data.source.local.PhotoDao
import nz.ac.canterbury.cosc680.plantplus.app.data.source.local.ResultDao
import nz.ac.canterbury.cosc680.plantplus.app.data.source.local.UserDao
import nz.ac.canterbury.cosc680.plantplus.app.data.source.local.WorkSpaceDao
import nz.ac.canterbury.cosc680.plantplus.app.domain.Photo
import kotlinx.coroutines.withContext
import kotlinx.serialization.descriptors.PrimitiveKind
import nz.ac.canterbury.cosc680.plantplus.app.domain.PredictResult
import nz.ac.canterbury.cosc680.plantplus.app.domain.User
import nz.ac.canterbury.cosc680.plantplus.app.domain.WorkSpace

class PlantlocalRepostiory( private val userDao: UserDao,
                            private val workSpaceDao:WorkSpaceDao,
                            private val predictResultDao: ResultDao,
                            private val photoDao: PhotoDao,
                            private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
){

    private val _allPhoto : MutableStateFlow<List<Photo>> =  MutableStateFlow(photoDao.getAll())
    val allPhoto:Flow<List<Photo>>
        get() = _allPhoto.asStateFlow()

    val numWorkSpace : Flow<Int> = workSpaceDao.getCount()

    private val _allWorkSpace  = MutableStateFlow(workSpaceDao.getAll())
    val allWorkSpace : Flow<List<WorkSpace>> = workSpaceDao.loadAll()


    private val _allPredictResult  = MutableStateFlow(predictResultDao.getAll())
    val allPredictResult:Flow<List<PredictResult>>
        get() = _allPredictResult.asStateFlow()

    fun getPhotoWithWorkSpaceId(workSpaceId: Long)  =
        photoDao.getPhotosByWorkSpaceID(workSpaceId)
            .switchMap {photos ->
                liveData {
                emit(photos)
            }
        }

    fun getPredictResultByPhotoId(photoId: Long, workSpaceId: Long) : Flow<List<PredictResult>> =
        // When the result of customSortFlow is available,
        // this will combine it with the latest value from
        // the flow above.  Thus, as long as both `plants`
        // and `sortOrder` are have an initial value (their
        // flow has emitted at least one value), any change
        // to either `plants` or `sortOrder`  will call
        // `plants.applySort(sortOrder)`.
        predictResultDao.getResultByPhotoId(photoId).flowOn(defaultDispatcher).conflate()

    fun pickPredictResultByPhotoId(photoId: Long, workSpaceId: Long) : List<PredictResult> =
    // When the result of customSortFlow is available,
    // this will combine it with the latest value from
    // the flow above.  Thus, as long as both `plants`
    // and `sortOrder` are have an initial value (their
    // flow has emitted at least one value), any change
    // to either `plants` or `sortOrder`  will call
        // `plants.applySort(sortOrder)`.
        predictResultDao.getResultByPhotoIdList(photoId)


    fun pickOnePhotoByWorkSpaceId(workSpaceId: Long) =
        photoDao.getOnePhotosByWorkSpaceID(workSpaceId).switchMap {photo ->
            androidx.lifecycle.liveData {
                emit(photo)
            }
        }


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getOnePhotoFromWorkSpace(workSpaceId:Int){

    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertUser(user:User){
        Log.d("D/insertUser",user.toString())
        userDao.insert(user)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateUser(user:User){
        Log.d("D/updateUser",user.toString())
        userDao.update(user)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteUser(user:User){
        Log.d("D/deleteUser",user.toString())
        userDao.delete(user)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertWorkSpace(workSpace: WorkSpace){
        Log.d("D/insertWorkSpace",workSpace.toString())
        workSpaceDao.insert(workSpace)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateWorkSpace(workSpace: WorkSpace){
        Log.d("D/updateWorkSpace",workSpace.toString())
        workSpaceDao.update(workSpace)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteWorkSpace(workSpace: WorkSpace){
        Log.d("D/updateWorkSpace",workSpace.toString())
        workSpaceDao.delete(workSpace)
    }


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertPredictResult(predictResult: PredictResult){
        Log.d("D/insertPredictResult",predictResult.toString())
        predictResultDao.insert(predictResult)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updatePredictResult(predictResult: PredictResult){
        Log.d("D/updatePredictResult",predictResult.toString())
        predictResultDao.update(predictResult)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deletePredictResult(predictResult: PredictResult){
        Log.d("D/deletePredictResult",predictResult.toString())
        predictResultDao.delete(predictResult)
    }




    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertPhoto( photo: Photo ) : Flow<Long> = flow{
        Log.d("D/insertPhoto",photo.toString())
        emit(photoDao.insert(photo))
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updatePhoto(photo: Photo){
        Log.d("D/updatePhoto",photo.toString())
        photoDao.update(photo)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deletePhoto(photo: Photo){
        Log.d("D/deletePredictResult",photo.toString())
        photoDao.delete(photo)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun loadPhotoByWorkSpaceId(workSpaceId: Long): List<Photo> {
        //Log.d("D/loadPhotoByTripId",workSpaceId?.toString())
        //return photoDao.getPhotosByWorkSpaceID(workSpaceId)
        TODO("loadPhotoByWorkSpaceId")
    }




}