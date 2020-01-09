package com.enginebai.project.utils.logging

import android.util.Log
import com.orhanobut.logger.Logger
import timber.log.Timber
import java.io.PrintWriter
import java.io.StringWriter

class MyCustomLoggingDebugTree : Timber.DebugTree() {

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        val logText = StringBuilder()
        logText.append("[${priorityToLogLevel(priority)}][$tag] $message\n")
        t?.run { logText.append(getStackTraceString(this)) }
        when (priority) {
            Log.VERBOSE -> Logger.v(logText.toString())
            Log.DEBUG -> Logger.d(logText.toString())
            Log.INFO -> Logger.i(logText.toString())
            Log.WARN -> Logger.w(logText.toString())
            Log.ERROR -> Logger.e(logText.toString())
            else -> Logger.wtf(logText.toString())
        }
    }

    private fun priorityToLogLevel(priority: Int): String {
        return when (priority) {
            Log.VERBOSE -> "VERBOSE"
            Log.DEBUG -> "DEBUG"
            Log.INFO -> "INFO"
            Log.WARN -> "WARN"
            Log.ERROR -> "ERROR"
            Log.ASSERT -> "ASSERT"
            else -> this.toString()
        }
    }

    // copy from https://github.com/JakeWharton/timber/blob/master/timber/src/main/java/timber/log/Timber.kt
    private fun getStackTraceString(t: Throwable): String {
        // Don't replace this with Log.getStackTraceString() - it hides
        // UnknownHostException, which is not what we want.
        val sw = StringWriter(256)
        val pw = PrintWriter(sw, false)
        t.printStackTrace(pw)
        pw.flush()
        return sw.toString()
    }

}