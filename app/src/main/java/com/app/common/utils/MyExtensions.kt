package com.app.common.utils

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager

fun View.hideKeyboard() {
    val inputManager: InputMethodManager = this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(this.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
}

fun Context.inNetworkAvailable(): Boolean {
    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val nw = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(nw)
        return actNw != null
    } else {
        return connectivityManager.activeNetworkInfo?.isConnected ?: false
    }
}
