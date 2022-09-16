package com.app.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.common.base.BaseViewModel
import com.app.common.data.network.APIInterface
import com.app.common.model.LoginModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivityViewModel(private val api: APIInterface) : BaseViewModel() {

    private val _login = MutableLiveData<LoginModel>()
    val login: LiveData<LoginModel> = _login

    fun callApi(username: String, password: String) {

        viewModelScope.launch(Dispatchers.IO) {
            _loader.postValue(true)
            val loginModel = api.getUserRxJava(username, password)
            withContext(Dispatchers.Main) {
//                Log.e("TAG", "callApi: " + loginModel.job + " " + loginModel.name)
                _login.postValue(loginModel)
            }
            _loader.postValue(false)
        }
        //RxJava
        /*val observable: Observable<LoginModel> = api.getUserRxJava(username, password)
        _loader.postValue(true)
        compositeDis.add(
            observable
                .subscribeOn(io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { loginModel ->
                    _loader.postValue(false)
                    _login.postValue(loginModel)
                }
        )*/
    }
}


