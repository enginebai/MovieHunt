package com.enginebai.base.view

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import org.koin.core.component.KoinComponent

abstract class BaseViewModel : ViewModel(), KoinComponent {
    private val disposables = CompositeDisposable()

    val loading by lazy { PublishSubject.create<Boolean>() }
    val errorMessage by lazy { PublishSubject.create<String>() }

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }

    fun Disposable.disposeOnCleared(): Disposable {
        disposables.add(this)
        return this
    }
}