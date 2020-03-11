package com.enginebai.base.utils

import android.app.Application
import com.enginebai.base.R
import io.reactivex.exceptions.OnErrorNotImplementedException
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.functions.Consumer
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.subjects.PublishSubject
import retrofit2.HttpException
import timber.log.Timber
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class RxErrorHandler(private val application: Application) : Consumer<Throwable> {

    val errorMessageToDisplay = PublishSubject.create<String>()

    init {
        RxJavaPlugins.setErrorHandler(this)
    }

    override fun accept(t: Throwable) {
        when (val cause = parseCause(t)) {
            is SocketTimeoutException, is ConnectException, is UnknownHostException, is SocketException -> {
                Timber.w("Network fail: $cause")
                errorMessageToDisplay.onNext(application.getString(R.string.error_network_fail))
            }
            is HttpException -> {
                val url = cause.response()?.raw()?.request?.url
                errorMessageToDisplay.onNext("HTTP ${cause.code()} of $url")
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
                t.cause?.run { return this } ?: throw ParseCauseFailException(t)
            }
        }
        return t
    }
}

class ParseCauseFailException(t: Throwable) : RuntimeException(t)