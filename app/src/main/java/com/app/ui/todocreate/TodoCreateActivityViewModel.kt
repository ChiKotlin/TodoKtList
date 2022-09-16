package com.app.ui.todocreate

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.common.alarm.AlarmReceiver
import com.app.common.base.BaseViewModel
import com.app.common.data.database.dao.TaskDao
import com.app.common.model.RoomModel
import com.app.common.utils.AppConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class TodoCreateActivityViewModel(val dao: TaskDao) : BaseViewModel() {

     var TAG = "TodoCreateActivityViewModel"

    private val _insert = MutableLiveData<RoomModel>()
    val insert: LiveData<RoomModel> = _insert

    private val _update = MutableLiveData<RoomModel>()
    val update: LiveData<RoomModel> = _update

    fun insert(
        title: String,
        salary: Int,
        desc: String,
        time: String,
        type: String,
    ) {
        //Coroutine with room database
        viewModelScope.launch(Dispatchers.IO) {
            val roomModel = RoomModel()
            roomModel.setTitle(title)
            roomModel.setSalary(salary)
            roomModel.setDescription(desc)
            roomModel.setTime(time)
            roomModel.setType(type)

            val data = dao.insert(roomModel)
            withContext(Dispatchers.Main) {
                for (i in data.indices) {
                    val id = data[i].toInt()
                    Log.e(TAG, "insert: $id")
                    roomModel.setId(id)
                    _insert.postValue(roomModel)
                }
            }
        }
        //RxJava
        /* val todoObservable = Observable.create { emitter: ObservableEmitter<RoomModel?> ->
             try {
                 val roomModel = RoomModel()
                 roomModel.setTitle(title)
                 roomModel.setSalary(salary)
                 roomModel.setDescription(desc)
                 roomModel.setTime(time)
                 roomModel.setType(type)

                 DataBaseClient.getInstance(MyApplication.getInstance())
                     .todoDao()
                     .insert(roomModel)

                 emitter.onNext(roomModel)
                 emitter.onComplete()

             } catch (ex: Exception) {
                 emitter.onError(ex)
             }
         }
         compositeDis.add(todoObservable
             .subscribeOn(Schedulers.io())
             .observeOn(AndroidSchedulers.mainThread())
             .subscribe { roomModel: RoomModel? ->
                 _insert.postValue(roomModel)
             })*/
    }

    fun update(
        taskTodo: RoomModel,
        title: String,
        salary: Int,
        desc: String,
        time: String,
        type: String,
    ) {
        //Coroutine
        viewModelScope.launch(Dispatchers.IO) {
            taskTodo.setTitle(title)
            taskTodo.setSalary(salary)
            taskTodo.setDescription(desc)
            taskTodo.setTime(time)
            taskTodo.setType(type)

            dao.update(taskTodo)
            withContext(Dispatchers.Main) {
                    _update.postValue(taskTodo)
            }
        }
        //RxJava
        /* val todoObservable = Observable.create { emitter: ObservableEmitter<RoomModel?> ->
             try {
                 taskTodo.setTitle(title)
                 taskTodo.setSalary(salary)
                 taskTodo.setDescription(desc)
                 taskTodo.setTime(time)
                 taskTodo.setType(type)

                 DataBaseClient.getInstance(MyApplication.getInstance())
                     .todoDao()
                     .update(taskTodo)

                 emitter.onNext(taskTodo)
                 emitter.onComplete()

             } catch (ex: Exception) {
                 emitter.onError(ex)
             }
         }
         compositeDis.add(todoObservable
             .subscribeOn(Schedulers.io())
             .observeOn(AndroidSchedulers.mainThread())
             .subscribe { roomModel: RoomModel? ->
                 _update.postValue(roomModel)
             })*/
    }

    fun notification(mCurrentTime: Calendar, context: TodoCreateActivity, title: String, id: Int) {
        val alarmManager: AlarmManager =
            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra(AppConstants.NOTIF_TITLE, title)
        val pendingIntent =
            PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        Log.e("TAG", "notification: " + mCurrentTime.timeInMillis + " " + id + " " + mCurrentTime.time)
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
            mCurrentTime.timeInMillis, 1000 * 60 * 20,
            pendingIntent)
    }
}

