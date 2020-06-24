package com.enginebai.base.view

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.koin.core.KoinComponent

abstract class BaseViewModel : ViewModel(), KoinComponent {
    private val disposables = CompositeDisposable()

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }

    protected fun Disposable.disposeOnCleared(): Disposable {
        disposables.add(this)
        return this
    }
}