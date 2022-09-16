package com.app.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.app.common.base.BaseActivity
import com.app.common.data.prefs.PrefsHelper
import com.app.common.utils.AppConstants
import com.app.common.utils.MyViewModelFactory
import com.app.common.utils.hideKeyboard
import com.app.common.utils.inNetworkAvailable
import com.app.ui.R
import com.app.ui.databinding.ActivityLoginBinding
import com.app.ui.todolist.TodoListActivity

class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {

    private lateinit var viewModel: LoginActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =
            ViewModelProvider(this, MyViewModelFactory(api))[LoginActivityViewModel::class.java]
        initView()
        if (!TextUtils.isEmpty(PrefsHelper.getInstance(this).getString(AppConstants.USERNAME))
            && !TextUtils.isEmpty(PrefsHelper.getInstance(this).getString(AppConstants.PASSWORD))
        ) {
            startActivity(Intent(this, TodoListActivity::class.java))
            finish()
        } else {
            setUpObserver()
        }
    }

    private fun setUpObserver() {
        viewModel.login.observe(this) {
            it?.let { loginModel ->
                if (loginModel.name == AppConstants.MORPHEUS && loginModel.job == AppConstants.LEADER) {
                    PrefsHelper.getInstance(this).setString(AppConstants.USERNAME, loginModel.name)
                    PrefsHelper.getInstance(this).setString(AppConstants.PASSWORD, loginModel.job)
                    startActivity(Intent(this, TodoListActivity::class.java))
                    finish()
                } else {
                    showToast(getString(R.string.user_not_found))
                }
            }
        }

        viewModel.loader.observe(this) {
            it?.let { loader ->
                if (loader) {
                    binding.progressBar.isVisible = true
                } else {
                    binding.progressBar.visibility = View.INVISIBLE
                }
            }
        }
    }

    private fun initView() {
        binding.loginBtn.setOnClickListener {
            if (this.inNetworkAvailable()) {
                when {
                    binding.editUserEdit.text?.length == 0 -> showToast(getString(R.string.please_username))
                    binding.editPasswordEdit.text?.length == 0 -> showToast(getString(R.string.please_password))
                    else -> {
                        it.hideKeyboard()
                        viewModel.callApi(
                            binding.editUserEdit.text.toString().trim(),
                            binding.editPasswordEdit.text.toString().trim()
                        )
                    }
                }
            } else {
                showToast(getString(R.string.no_network))
            }
        }
    }
}