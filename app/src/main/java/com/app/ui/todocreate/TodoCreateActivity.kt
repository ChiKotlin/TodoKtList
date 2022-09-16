package com.app.ui.todocreate

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.TimePicker
import androidx.lifecycle.ViewModelProvider
import com.app.common.base.BaseActivity
import com.app.common.model.RoomModel
import com.app.common.utils.AppConstants
import com.app.common.utils.MyViewModelFactory
import com.app.ui.R
import com.app.ui.databinding.ActivityTodoCreateBinding
import java.util.*

class TodoCreateActivity : BaseActivity<ActivityTodoCreateBinding>(R.layout.activity_todo_create) {

    private lateinit var viewModel: TodoCreateActivityViewModel
    private lateinit var type: String
    private var taskTodo: RoomModel? = null
    private lateinit var mCurrentTime: Calendar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            MyViewModelFactory(dao)
        )[TodoCreateActivityViewModel::class.java]

        setUpdateTodo()
        initView()
        openTimePickerBtn()
        setUpObserver()
    }

    private fun setUpdateTodo() {
        val bundle: Bundle? = intent.extras
        bundle.apply {
            taskTodo = intent.getSerializableExtra(AppConstants.TODOTASK) as RoomModel?
            if (taskTodo != null) {
                binding.editTitle.setText(taskTodo!!.getTitle())
                binding.editDescription.setText(taskTodo!!.getDescription())
                binding.editTime.text = taskTodo!!.getTime()
                binding.editSalary.setText(taskTodo!!.getSalary().toString())
                getRadioBtn(taskTodo!!.getType())
            }
        }
    }

    private fun getRadioBtn(type: String?) {
        if (type != null) {
            when (type) {
                binding.radioDaily.text.toString() -> {
                    binding.radioDaily.isChecked = true
                    this.type = getString(R.string.daily)
                }
                binding.radioMonthly.text.toString() -> {
                    binding.radioMonthly.isChecked = true
                    this.type = getString(R.string.monthly)
                }
                binding.radioYearly.text.toString() -> {
                    binding.radioYearly.isChecked = true
                    this.type = getString(R.string.yearly)
                }
            }
        }
    }

    private fun setUpObserver() {
        viewModel.insert.observe(this) {
            showToast(getString(R.string.insert))
            viewModel.notification(
                mCurrentTime, this,
                binding.editTitle.text.toString(), it.getId()
            )
            finish()
        }

        viewModel.update.observe(this) {
            showToast(getString(R.string.updated))
            viewModel.notification(
                mCurrentTime, this,
                binding.editTitle.text.toString(), it.getId()
            )
            finish()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun openTimePickerBtn() {
        binding.imageTimePicker.setOnClickListener {
            mCurrentTime = Calendar.getInstance()
            val hour = mCurrentTime.get(Calendar.HOUR_OF_DAY)
            val minute = mCurrentTime.get(Calendar.MINUTE)
            val mTimePicker = TimePickerDialog(this,
                { _: TimePicker?, hourOfDay: Int, minute1: Int ->
                    var timeSet = resources.getString(R.string.am)
                    if (hourOfDay > 11)
                        timeSet = resources.getString(R.string.pm)

                    mCurrentTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    mCurrentTime.set(Calendar.MINUTE, minute1)
                    mCurrentTime.set(Calendar.SECOND, 0)
                    mCurrentTime.set(Calendar.MILLISECOND, 0)
                    binding.editTime.text = "$hourOfDay:$minute1:$timeSet"
                }, hour, minute, true
            )
            mTimePicker.show()
        }
    }

    private fun initView() {
        binding.createTodoBtn.setOnClickListener {
            when {
                binding.editTitle.text.toString().trim()
                    .isEmpty() -> showToast(getString(R.string.enter_title))
                binding.editSalary.text.toString().trim()
                    .isEmpty() -> showToast(getString(R.string.enter_salary))
                binding.editDescription.text.toString().trim()
                    .isEmpty() -> showToast(getString(R.string.enter_desc))
                binding.editTime.text.toString().trim()
                    .isEmpty() -> showToast(getString(R.string.enter_time))
                else -> {
                    if (binding.radioDaily.isChecked || binding.radioMonthly.isChecked || binding.radioYearly.isChecked) {
                        if (taskTodo != null) {
                            viewModel.update(
                                taskTodo!!, binding.editTitle.text.toString().trim(),
                                binding.editSalary.text.toString().toInt(),
                                binding.editDescription.text.toString().trim(),
                                binding.editTime.text.toString().trim(), type
                            )
                        } else {
                            viewModel.insert(
                                binding.editTitle.text.toString().trim(),
                                binding.editSalary.text.toString().toInt(),
                                binding.editDescription.text.toString().trim(),
                                binding.editTime.text.toString().trim(), type
                            )
                        }
                    } else {
                        showToast(getString(R.string.select_type))
                    }
                }
            }
        }
    }

    fun onRadioButtonClicked(view: View) {
        val checked = (view as RadioButton).isChecked
        when (view.id) {
            R.id.radioDaily -> {
                if (checked) binding.radioDaily.isChecked = true
                binding.radioMonthly.isChecked = false
                binding.radioYearly.isChecked = false
                type = getString(R.string.daily)
            }
            R.id.radioMonthly -> {
                if (checked) binding.radioMonthly.isChecked = true
                binding.radioDaily.isChecked = false
                binding.radioYearly.isChecked = false
                type = getString(R.string.monthly)
            }

            R.id.radioYearly -> {
                if (checked) binding.radioYearly.isChecked = true
                binding.radioDaily.isChecked = false
                binding.radioMonthly.isChecked = false
                type = getString(R.string.yearly)
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_up_bottom, R.anim.slide_out_up_bottom)
    }
}