package nz.ac.canterbury.cosc680.plantplus.app.di

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import nz.ac.canterbury.cosc680.plantplus.app.data.source.PlantlocalRepostiory
import nz.ac.canterbury.cosc680.plantplus.app.domain.WorkSpace

class WorkSpaceViewModel(private val plantlocalRepostiory: PlantlocalRepostiory) :ViewModel(){

    /**
     * Operation of worksace management
     *
     * */
    private var _showlogout = MutableStateFlow(false)
    var showlogout = _showlogout.asStateFlow()

    val numWorkSpace: LiveData<Int> = plantlocalRepostiory.numWorkSpace.asLiveData()

    // all workspace
    val allWorkSpace: LiveData<List<WorkSpace>> = plantlocalRepostiory.allWorkSpace.asLiveData()

    private var _currentWorkSpace = MutableStateFlow(WorkSpace())
    var currentWorkSpace = _currentWorkSpace.asStateFlow()

    fun onSetCurrentWorkSpace( workSpace: WorkSpace ){
        _currentWorkSpace.value = workSpace
    }
    fun onSetshowlogout( bEnable:Boolean = false){
        _showlogout.value = bEnable
    }


    fun addWorkSpace(workSpace: WorkSpace ) = viewModelScope.launch {
        plantlocalRepostiory.insertWorkSpace(workSpace)
    }

    fun updateWorkSpace(workSpace: WorkSpace ) = viewModelScope.launch {
        plantlocalRepostiory.updateWorkSpace(workSpace)
    }

    fun deleteWorkSpace(workSpace: WorkSpace ) = viewModelScope.launch {
        plantlocalRepostiory.deleteWorkSpace(workSpace)
    }
}


class WorkSpaceViewModelFactory(private  val plantlocalRepostiory: PlantlocalRepostiory) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WorkSpaceViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WorkSpaceViewModel(plantlocalRepostiory) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}