package nz.ac.canterbury.cosc680.plantplus.app.di

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import nz.ac.canterbury.cosc680.plantplus.app.data.source.PlantlocalRepostiory
import nz.ac.canterbury.cosc680.plantplus.app.domain.Photo

class PhotoViewModel(private val plantlocalRepostiory: PlantlocalRepostiory, ) : ViewModel() {

    /**
     * Operation of worksace management
     *
     * */

    // all workspace
    private val _workSpacePhotos = MutableStateFlow( emptyList<Photo>() )
    val workSpacePhotos: StateFlow<List<Photo>> = _workSpacePhotos.asStateFlow()

    var workSpaceId :Int = 0
    //val pickOneWorkSpacePhoto : Flow<Photo> = plantlocalRepostiory.getOnePhotoFromWorkSpace(workSpaceId)


    fun getPhotosByWorkSpaceId(id: Long) =  viewModelScope.launch{
        plantlocalRepostiory.loadPhotoByWorkSpaceId(id)
    }

    fun getWorkSpacePhotos(workSpaceId: Long) = plantlocalRepostiory.getPhotoWithWorkSpaceId(workSpaceId)

    fun pickOnePhotoByWorkSpaceId(workSpaceId:Long)  =  plantlocalRepostiory.pickOnePhotoByWorkSpaceId(workSpaceId)

    fun onLoadPhotoByWorkSpaceID(workSpaceId: Long) = viewModelScope.launch {
        if (workSpaceId > 0) {
            _workSpacePhotos.value = plantlocalRepostiory.loadPhotoByWorkSpaceId(workSpaceId)
        }
    }



    fun addPhoto(photo: Photo) = viewModelScope.launch {
        plantlocalRepostiory.insertPhoto(photo)
            .flowOn(Dispatchers.IO)
            .catch { e->
                Log.e("addPhoto", "${e.message}")
            }
            .collect(){
                Log.i("addPhoto",it.toString())
                // update UI, set the inserted photoId back to UI
                photo.photoId = it
        }
    }

    fun updatePhoto(photo: Photo) = viewModelScope.launch {
        plantlocalRepostiory.updatePhoto(photo)
    }

    fun deletePhoto(photo: Photo) = viewModelScope.launch {
        plantlocalRepostiory.deletePhoto(photo)
    }

}

class PhotoViewModelViewModelFactory(private  val plantlocalRepostiory: PlantlocalRepostiory) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PhotoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PhotoViewModel(plantlocalRepostiory) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}