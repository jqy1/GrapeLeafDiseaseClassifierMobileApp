package nz.ac.canterbury.cosc680.plantplus.app.di


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import nz.ac.canterbury.cosc680.plantplus.PlantApp
import nz.ac.canterbury.cosc680.plantplus.app.data.source.PlantlocalRepostiory
import nz.ac.canterbury.cosc680.plantplus.app.domain.Photo
import nz.ac.canterbury.cosc680.plantplus.app.domain.PredictResult
import nz.ac.canterbury.cosc680.plantplus.app.domain.WorkSpace
import kotlinx.coroutines.launch

class PredictResultViewModel : ViewModel(){

    var plantlocalRepostiory: PlantlocalRepostiory = PlantApp.instance.plantlocalRepostiory

    private val _workSpace = MutableStateFlow<WorkSpace>(WorkSpace())
    private val _photo = MutableStateFlow<Photo>(Photo())

    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
    private var errorMsg = ""

    fun setWorkSpaceAndPhotoId(photo: Photo, workSpace: WorkSpace){
        _workSpace.value = workSpace
        _photo.value = photo

        launchDataLoad{
            getPredictResults()
        }
    }

    private fun getPredictResults(){
        _predictResults.value = emptyList()
        _predictResults.value = plantlocalRepostiory.pickPredictResultByPhotoId( photoId = _photo.value.photoId, workSpaceId = _workSpace.value.workSpaceID)
        _predictResults.value?.let { predictResults1 ->
            predictResults1.forEach(){
                Log.d("launchDataload","$it")
            }
        }
    }


    private val _predictResults = MutableStateFlow<List<PredictResult>>(emptyList())
    val predictResults :LiveData<List<PredictResult>> = _predictResults.asLiveData()

    init {

        _predictResults.mapLatest { results ->
            if (_workSpace.value.workSpaceID == 0L  ) {

            } else {

            }
        }
        .onEach {  }
        .catch { throwable ->  errorMsg = throwable.message.toString() }
        .launchIn(viewModelScope)

    }

    /**
     * Helper function to call a data load function with a loading spinner; errors will trigger a
     * predictResults.
     *
     * By marking [block] as [suspend] this creates a suspend lambda which can call suspend
     * functions.
     *
     * @param block lambda to actually load data. It is called in the viewModelScope. Before calling
     *              the lambda, the loading spinner will display. After completion or error, the
     *              loading spinner will stop.
     */
    private fun launchDataLoad(block: suspend () -> Unit): Job {
        return viewModelScope.launch {
            try {
                block()
            } catch (error: Throwable) {

            } finally {
            }
        }
    }

}