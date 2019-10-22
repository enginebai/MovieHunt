package com.enginebai.project.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseActivity : AppCompatActivity() {

    private val disposables = CompositeDisposable()

    @LayoutRes
    abstract fun getLayoutId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
    }

    override fun onDestroy() {
        disposables.clear()
        super.onDestroy()
    }

    fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }
}