package com.app.common.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.common.base.BaseViewModel
import com.app.common.data.database.dao.TaskDao
import com.app.common.data.network.APIInterface
import com.app.ui.login.LoginActivityViewModel
import com.app.ui.todocreate.TodoCreateActivityViewModel
import com.app.ui.todolist.TodoListActivityViewModel

class MyViewModelFactory : ViewModelProvider.Factory {
    var dao: TaskDao? = null
    var api: APIInterface? = null

    constructor(api: APIInterface) {
        this.api = api
    }

    /*constructor(dao: TaskDao, api: APIInterface) : this(api) {
        this.dao = dao
    }
    */

    constructor(dao: TaskDao) {
        this.dao = dao
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when (modelClass) {
            LoginActivityViewModel::class.java -> {
                LoginActivityViewModel(api!!) as T
            }
            TodoCreateActivityViewModel::class.java -> {
                TodoCreateActivityViewModel(dao!!) as T
            }
            TodoListActivityViewModel::class.java -> {
                TodoListActivityViewModel(dao!!) as T
            }
            else -> BaseViewModel() as T
        }
    }
}