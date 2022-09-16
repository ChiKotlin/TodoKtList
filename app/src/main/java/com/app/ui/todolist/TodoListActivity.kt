package com.app.ui.todolist

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.OverScroller
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.common.alarm.AlarmReceiver
import com.app.common.base.BaseActivity
import com.app.common.model.RoomModel
import com.app.common.utils.AppConstants
import com.app.common.utils.MyViewModelFactory
import com.app.common.utils.RVEmptyObserver
import com.app.ui.R
import com.app.ui.adapter.RoomAdapter
import com.app.ui.databinding.ActivityTodoListBinding
import com.app.ui.login.LoginActivity
import com.app.ui.todocreate.TodoCreateActivity
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper

class TodoListActivity : BaseActivity<ActivityTodoListBinding>(R.layout.activity_todo_list) {

    lateinit var roomAdapter: RoomAdapter
    //Direct init viewModel using activity library
    private val viewModel: TodoListActivityViewModel by viewModels { MyViewModelFactory(dao) }
    private var taskTodo: RoomModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //manually init viewModel
        /* viewModel = ViewModelProvider(this,MyViewModelFactory(dao))[TodoListActivityViewModel::class.java]*/
        roomAdapter = RoomAdapter()
        val bundle: Bundle? = intent.extras
        bundle.apply {
            taskTodo = intent.getSerializableExtra(AppConstants.TODOTASK) as RoomModel?
        }
        initView()
        setUpObserver()
        insertTodoBtn()
        logoutBtn()
    }

    private fun logoutBtn() {
        binding.imageLogout.setOnClickListener {
            prefs.preferClear()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun initView() {
        binding.recyclerViewList.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewList.adapter = roomAdapter
        roomAdapter.registerAdapterDataObserver(RVEmptyObserver(binding.txtNoData,
            binding.recyclerViewList))
        OverScrollDecoratorHelper.setUpStaticOverScroll(binding.recyclerViewList, OverScrollDecoratorHelper.ORIENTATION_VERTICAL)
        roomAdapter.setOnItemClickListener(object : RoomAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                taskTodo = roomAdapter.roomModelList[position]
                val intent = Intent(this@TodoListActivity, TodoCreateActivity::class.java)
                intent.putExtra(AppConstants.TODOTASK, taskTodo)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up)
            }

            override fun onDeleteClick(position: Int) {
                taskTodo = roomAdapter.roomModelList[position]
                cancelAlarm(taskTodo!!.getId())
                viewModel.todoDelete(taskTodo!!.getId())
            }
        })
    }

    private fun cancelAlarm(id: Int) {
        val alarmManager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, id, intent, 0)
        alarmManager.cancel(pendingIntent)
    }

    private fun setUpObserver() {

        viewModel.getItemAndPersonList().observe(this, {
            roomAdapter.addAll(it)
        })

        /*viewModel.getTodos().observe(this, {
             roomAdapter.addAll(it)
         })*/

        viewModel.delete.observe(this, {})

        viewModel.loader.observe(this, {
            if (it) {
                binding.progressBarList.visibility = View.VISIBLE
            } else {
                binding.progressBarList.visibility = View.GONE
            }
        })
    }

    private fun insertTodoBtn() {
        binding.btnSubmit.setOnClickListener {
            startActivity(Intent(this, TodoCreateActivity::class.java))
        }
    }
}