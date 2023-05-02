package nz.ac.canterbury.cosc680.plantplus.app.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import nz.ac.canterbury.cosc680.plantplus.PlantApp
import nz.ac.canterbury.cosc680.plantplus.app.data.remote.Response
import nz.ac.canterbury.cosc680.plantplus.app.data.remote.Result
import nz.ac.canterbury.cosc680.plantplus.app.data.remote.ServicesRepository
import nz.ac.canterbury.cosc680.plantplus.app.data.source.PlantlocalRepostiory
import nz.ac.canterbury.cosc680.plantplus.app.domain.User

class UserServiceViewModel(private val servicesRepository: ServicesRepository,
                           private val plantlocalRepostiory: PlantlocalRepostiory) : ViewModel(){

    var _currentUser = MutableStateFlow(User())
    var currentUser = _currentUser.asStateFlow()


    fun setRegisterStatus(isRegister : Boolean)= viewModelScope.launch {
        if(isRegister)
            _authtoken.value = ""
    }

    fun currentRegisterUser(firstname:String = "", surname:String = "",email:String = "",userName:String = "", password:String = "", ) = viewModelScope.launch {
        // set the default value = "", it states that we do a login operation
        _authtoken.value = ""
        var result = servicesRepository.register(firstname,surname,email,userName,password)
        when(result){
            is Result.Success<Response<User>> -> // Happy path
            {
                //Log.d("currentWeacher-${Thread.currentThread().name}", "{${result.data.t.date}-${result.data.t.templature} succeed!")
                _authtoken.value = PlantApp.instance.authToken
            }
            is Exception -> // Show error in UI
            {
                _authtoken.value = ""
                //Log.e("currentWeacher-${Thread.currentThread().name}", "${result.stackTrace} get current errir!")
            }
            else -> {}
        }
    }


    private var _authtoken = MutableStateFlow("")
    var authtoken = _authtoken.asStateFlow()


    fun setLoginStatu(isLogin : Boolean)= viewModelScope.launch {
        if (isLogin) {
            _authtoken.value = ""
            PlantApp.instance.isLogin = true
        }
    }


    fun currentLoginUser(userName:String = "", password:String = "") = viewModelScope.launch {
        // set the default value = "", it states that we do a login operation
        _authtoken.value = ""
       var result = servicesRepository.login(userName,password)
       when(result){
           is Result.Success<Response<User>> -> // Happy path
           {
               //Log.d("currentWeacher-${Thread.currentThread().name}", "{${result.data.t.date}-${result.data.t.templature} succeed!")
               _authtoken.value = PlantApp.instance.authToken
           }
           is Exception -> // Show error in UI
           {
               _authtoken.value = ""
               //Log.e("currentWeacher-${Thread.currentThread().name}", "${result.stackTrace} get current errir!")
           }
           else -> {}
       }
    }
}

class UserServiceViewModelFactory(private val repository: ServicesRepository, private  val plantlocalRepostiory: PlantlocalRepostiory) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserServiceViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserServiceViewModel(repository,plantlocalRepostiory) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}