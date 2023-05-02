package nz.ac.canterbury.cosc680.plantplus.app.di

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import nz.ac.canterbury.cosc680.plantplus.app.data.remote.Response
import nz.ac.canterbury.cosc680.plantplus.app.data.remote.Result
import nz.ac.canterbury.cosc680.plantplus.app.data.remote.PredictRepository
import nz.ac.canterbury.cosc680.plantplus.app.data.source.PlantlocalRepostiory
import nz.ac.canterbury.cosc680.plantplus.app.domain.Photo
import nz.ac.canterbury.cosc680.plantplus.app.domain.PredictResult
import nz.ac.canterbury.cosc680.plantplus.app.domain.WorkSpace

class PredictServiceViewModel(private val predictRepository: PredictRepository,
                              private val plantlocalRepostiory: PlantlocalRepostiory) : ViewModel(){

    private var _response = MutableStateFlow(String())
    var response = _response.asStateFlow()

    private var _predictResults = MutableStateFlow(emptyList<PredictResult>())
    var predictResults = _predictResults.asStateFlow()


    /**
     * Operation of predict result management
     *
     * */

    // all predict result
    val allPredictResult: LiveData<List<PredictResult>> = plantlocalRepostiory.allPredictResult.asLiveData()


    fun addPredictResult(predictResult: PredictResult) = viewModelScope.launch {
        plantlocalRepostiory.insertPredictResult(predictResult)
    }

    fun updatePredictResult(predictResult: PredictResult) = viewModelScope.launch {
        plantlocalRepostiory.updatePredictResult(predictResult)
    }

    fun deleteWorkSpace(predictResult: PredictResult) = viewModelScope.launch {
        plantlocalRepostiory.deletePredictResult(predictResult)
    }

    fun setPredictResults(results:List<PredictResult>){
        _predictResults.value = emptyList<PredictResult>()

        _predictResults.value = results
    }

    fun predict(context:Context,uri : Uri, workSpace: WorkSpace = WorkSpace(), photo:Photo = Photo()) = viewModelScope.launch {
        _response.value = ""
        _predictResults.value = emptyList<PredictResult>()
       var result :Result<Response<List<PredictResult>>> = predictRepository.predict(context,uri )
       when(result){
           is Result.Success -> // Happy path
           {
               val results = result.data.t
               _predictResults.value = result.data.t

               //Save predict result into databases;
               results.forEach(){predictResult->
                   predictResult.photoId = photo.photoId
               }

           }
           is Exception -> // Show error in UI
           {
               Log.d("predict", "$workSpace - $photo")
               _predictResults.value = emptyList<PredictResult>()
           }
           else -> {
               Log.d("predict", "$workSpace - $photo")
               _predictResults.value = emptyList<PredictResult>()
           }
       }
    }


    fun feedback(context:Context,uri : Uri, disease: String) = viewModelScope.launch {
        var result :Result<Response<String>> = predictRepository.feedback(context,uri, disease)
        when(result){
            is Result.Success -> // Happy path
            {

            }
            is Exception -> // Show error in UI
            {

            }
            else -> {}
        }
    }
}





class PredictServiceViewModelFactory(private val repository: PredictRepository, private  val plantlocalRepostiory: PlantlocalRepostiory) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PredictServiceViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PredictServiceViewModel(repository,plantlocalRepostiory) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}