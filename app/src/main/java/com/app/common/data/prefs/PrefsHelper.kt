package com.app.common.data.prefs

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import com.app.common.utils.AppConstants

class PrefsHelper @SuppressLint("CommitPrefEdits") private constructor(context: Context) {

    private var sharedPreferences: SharedPreferences? = null
    private var editor: Editor? = null

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var INSTANCE: PrefsHelper? = null
        @Synchronized
        fun getInstance(context: Context): PrefsHelper {
            if (INSTANCE == null) {
                INSTANCE = PrefsHelper(context)
            }
            return INSTANCE!!
        }
    }

    init {
        sharedPreferences = context.getSharedPreferences(AppConstants.PREFS_NAME, Context.MODE_PRIVATE)
        editor = sharedPreferences?.edit()
    }

    fun preferClear() {
        editor?.clear()
        editor?.apply()
    }

    fun setString(key: String, value: String) {
        editor?.putString(key, value)
        editor?.apply()
    }

    fun getString(key: String?): String? {
        return sharedPreferences?.getString(key, null)
    }

    /*fun customPrefs(context: Context): SharedPreferences =
        context.getSharedPreferences("TodoPrefs", Context.MODE_PRIVATE)

    private inline fun SharedPreferences.edit(operation: (Editor) -> Unit) {
        val editor = this.edit()
        operation(editor)
        editor.apply()
    }

    operator fun SharedPreferences.set(key: String, value: Any?) = when (value) {
        is String? -> edit { it.putString(key, value) }
        is Int -> edit { it.putInt(key, value) }
        is Boolean -> edit { it.putBoolean(key, value) }
        is Float -> edit { it.putFloat(key, value) }
        is Long -> edit { it.putLong(key, value) }
        else -> throw UnsupportedOperationException("Not yet implemented")
    }

    inline operator fun <reified T : Any> SharedPreferences.get(
        key: String,
        defaultValue: T? = null
    ): T = when (T::class) {
        String::class -> getString(key, defaultValue as? String ?: "") as T
        Int::class -> getInt(key, defaultValue as? Int ?: -1) as T
        Boolean::class -> getBoolean(key, defaultValue as? Boolean ?: false) as T
        Float::class -> getFloat(key, defaultValue as? Float ?: -1f) as T
        Long::class -> getLong(key, defaultValue as? Long ?: -1) as T
        else -> throw UnsupportedOperationException("Not yet implemented")
    }*/
}
