package com.app.ui.todolist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.common.base.BaseViewModel
import com.app.common.data.database.dao.TaskDao
import com.app.common.model.RoomModel
import io.reactivex.ObservableEmitter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TodoListActivityViewModel(private val dao: TaskDao) : BaseViewModel() {

    private val _todoLists = MutableLiveData<List<RoomModel>>()
    val todoLists: LiveData<List<RoomModel>> = _todoLists

    private val _delete = MutableLiveData<Boolean>()
    val delete: LiveData<Boolean> = _delete

    private val _deleteId = MutableLiveData<Long>()
    val deleteId: LiveData<Long> = _deleteId

    //LiveData used
    fun getItemAndPersonList(): LiveData<List<RoomModel>> {
        return dao.getAll()
    }

    fun getTodos() {
        //Coroutine with room database
        /* viewModelScope.launch(Dispatchers.IO) {
             _loader.postValue(true)
            val todoList: List<RoomModel> = dao.getAll()
             withContext(Dispatchers.Main) {
                 _todoLists.postValue(todoList)
             }
             _loader.postValue(false)
         }*/

        //RxJava
        /*val observable = Observable.create { emitter: ObservableEmitter<List<RoomModel>> ->

            try {
                val todoList: List<RoomModel> =
                    DataBaseClient.getInstance(MyApplication.getInstance())
                        .todoDao()
                        .getAll()

                emitter.onNext(todoList)
                emitter.onComplete()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        _loader.postValue(true)
        compositeDis.add(observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ value: List<RoomModel> ->
                viewModelScope.launch {
                    delay(2000)
                    _loader.postValue(false)
                    _todoLists.postValue(value)
                }
            }, {
                _loader.postValue(false)
            }))*/
    }

    fun todoDelete(task: Int) {
        //Coroutine with room database
        /* viewModelScope.launch(Dispatchers.IO) {


             dao.delete(deleteList)

             withContext(Dispatchers.Main) {



                 _delete.postValue(true)
             }
         }*/
        //RxJava
        val todoObservable = io.reactivex.Observable.create { emitter: ObservableEmitter<Boolean> ->
            try {
                dao.deleteByItemId(task.toLong())
                emitter.onNext(true)
                emitter.onComplete()
            } catch (e: java.lang.Exception) {
                emitter.onNext(false)
            }
        }

        disposable.add(todoObservable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                _delete.postValue(true)
            })
    }
}