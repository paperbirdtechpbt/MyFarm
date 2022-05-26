package com.pbt.myfarm.Util

import android.content.Context
import android.text.format.DateFormat

class CustomExceptionHandler(
    private val localPath: String?,
    url: String,
    context: Context
) :
    Thread.UncaughtExceptionHandler {

    private val defaultUEH: Thread.UncaughtExceptionHandler =
        Thread.getDefaultUncaughtExceptionHandler()
    var context: Context = context

    override fun uncaughtException(t: Thread, e: Throwable) {

        val timestamp = DateFormat.format("yyyy-MM-dd_kk:mm:ss", java.util.Date()) as String
        val result: java.io.Writer = java.io.StringWriter()
        val printWriter: java.io.PrintWriter = java.io.PrintWriter(result)
        e.printStackTrace(printWriter)
        val stacktrace: String = result.toString()
        printWriter.close()
        var filename = ""
        var packageInfo: android.content.pm.PackageInfo? = null
        try {
            packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        } catch (e12: android.content.pm.PackageManager.NameNotFoundException) {
        }
        val versionName: String? = packageInfo?.versionName

        filename = MySharedPreference.getUser(context)?.let {
            "ID_${it.id}_Name${it.name}_Version_" + versionName + "_" + android.os.Build.MODEL + timestamp + ".txt"
        } ?: "Version_" + versionName + "_" + android.os.Build.MODEL + timestamp + ".txt"



        if (localPath != null) {
            writeToFile(stacktrace, filename)
        }
        defaultUEH.uncaughtException(t, e)
    }

    private fun writeToFile(stacktrace: String, filename: String) {
        try {
            val bos: java.io.BufferedWriter = java.io.BufferedWriter(
                java.io.FileWriter(
                    "$localPath/$filename"
                )
            )
            bos.write(stacktrace)
            bos.flush()
            bos.close()
        } catch (e: java.lang.Exception) {
            android.util.Log.d("CrashFileWrite", "Exception e" + e.message)
            e.printStackTrace()
        }
    }

    companion object {

    }

}