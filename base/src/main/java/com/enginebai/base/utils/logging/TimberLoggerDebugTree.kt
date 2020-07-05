package com.enginebai.base.utils.logging

import android.util.Log
import com.orhanobut.logger.Logger
import timber.log.Timber

class TimberLoggerDebugTree : Timber.DebugTree() {

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        when (priority) {
            Log.VERBOSE -> Logger.v(message)
            Log.DEBUG -> Logger.d(message)
            Log.INFO -> Logger.i(message)
            Log.WARN -> Logger.w(message)
            Log.ERROR -> Logger.e(t, message)
            else -> Logger.wtf(message)
        }
    }
}