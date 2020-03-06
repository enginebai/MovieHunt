package com.enginebai.moviehunt.utils

import android.app.Application
import com.enginebai.moviehunt.R
import io.reactivex.exceptions.OnErrorNotImplementedException
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.functions.Consumer
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class RxErrorHandler(private val application: Application) : Consumer<Throwable> {

    // handled exception to display corresponding error message
    val errorMessageToDisplay = BehaviorSubject.create<String>()

    init {
        RxJavaPlugins.setErrorHandler(this)
    }

    override fun accept(t: Throwable) {
        when (val cause = parseCause(t)) {
            is SocketTimeoutException, is ConnectException, is UnknownHostException, is SocketException -> {
                Timber.w("Network fail: $cause")
                errorMessageToDisplay.onNext(application.getString(R.string.error_network_fail))
            }
            else -> {
                Timber.e(cause)
                throw cause
            }
        }
    }

    private fun parseCause(t: Throwable): Throwable {
        when (t) {
            is OnErrorNotImplementedException, is UndeliverableException, is RuntimeException -> {
                return t.cause?.run { return this } ?: ParseCauseFailException(t)
            }
        }
        return t
    }
}

class ParseCauseFailException(t: Throwable) : RuntimeException(t)