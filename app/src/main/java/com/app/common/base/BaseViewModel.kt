package com.app.common.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.common.data.database.DataBaseClient
import com.app.common.data.network.APIClient
import com.app.common.data.network.APIInterface
import com.app.ui.MyApplication

import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel : ViewModel() {

    val disposable: CompositeDisposable = CompositeDisposable()
    val _loader = MutableLiveData<Boolean>()
    val loader: LiveData<Boolean> = _loader

    /*override fun onCleared() {
        super.onCleared()
        //compositeDis.clear()
    }*/

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}