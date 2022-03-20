package com.enginebai.base.view

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import timber.log.Timber

abstract class BaseActivity : AppCompatActivity() {

    private val disposables = CompositeDisposable()

    override fun onDestroy() {
        disposables.clear()
        super.onDestroy()
    }

    open fun handleErrorMessage(message: String) {
        Timber.w(message)
    }

    protected fun Disposable.disposeOnDestroy(): Disposable {
        disposables.add(this)
        return disposables
    }
}