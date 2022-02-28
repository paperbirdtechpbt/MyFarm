package com.pbt.myfarm.Util

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.pbt.myfarm.HttpResponse.loginResult
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_SHARED_PREF_NAME
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_SHARED_PREF_TOKEN

class MySharedPreference  {


    companion object {

        var preferences: SharedPreferences? = null

        fun openPref(context: Context) {
            preferences =
                context.getSharedPreferences(CONST_SHARED_PREF_NAME, Context.MODE_PRIVATE)
        }

        fun openTokenPref(context: Context) {
            preferences =
                context.getSharedPreferences(CONST_SHARED_PREF_TOKEN, Context.MODE_PRIVATE)


        }

        fun setStringValue(context: Context, key: String?, value: String?) {
            openPref(context)
            val editor: SharedPreferences.Editor = preferences!!.edit()
            editor.putString(key, value)
            editor.commit()
            preferences = null
        }

        fun setStringTokenValue(context: Context, key: String?, value: String?) {
            openTokenPref(context)
            val editor: SharedPreferences.Editor = preferences!!.edit()
            editor.putString(key, value)
            editor.commit()
            preferences = null
        }

        fun setIntValue(context: Context, key: String?, value: Int) {
            openPref(context)
            val editor: SharedPreferences.Editor =
                preferences!!.edit()
            editor.putInt(key, value)
            editor.commit()
            preferences = null
        }

        fun getStringValue(context: Context, key: String?, defvalue: String?): String? {
            openPref(context)
            val result: String? =
                preferences?.getString(key, defvalue)
            preferences = null
            return result
        }
        fun getStringTokenValue(context: Context, key: String?, defvalue: String?): String? {
            openTokenPref(context)
            val result: String? =
                preferences?.getString(key, defvalue)
            preferences = null
            return result
        }

        fun getintValue(context: Context, key: String?, defvalue: Int): Int? {
            openPref(context)
            val result: Int? = preferences?.getInt(key, defvalue)
            preferences = null
            return result
        }

        fun clearPref(context: Context) {
            openPref(context)
            val editor: SharedPreferences.Editor? =
                preferences?.edit()
            editor?.clear()
            editor?.commit()
        }

        fun getUser(context: Context): loginResult? {
            openPref(context)
            val user: loginResult? = Gson().fromJson(
                preferences?.getString(AppConstant.CONST_SHARED_PREF_USERNAME, ""),
                loginResult::class.java
            )
            preferences = null
            return user
        }
    }

}