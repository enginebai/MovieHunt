package com.enginebai.base.view

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.enginebai.base.utils.RxErrorHandler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.koin.android.ext.android.inject
import java.util.concurrent.TimeUnit

abstract class BaseActivity : AppCompatActivity() {

    private val disposables = CompositeDisposable()
    private val rxErrorHandler: RxErrorHandler by inject()
    private var rxErrorDisposable: Disposable? = null

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun handleErrorMessage(message: String)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
    }

    override fun onStart() {
        super.onStart()
        if (null == rxErrorDisposable || false == rxErrorDisposable?.isDisposed) {
            rxErrorDisposable = rxErrorHandler.errorMessageToDisplay
                .filter { it.isNotBlank() }
                .throttleFirst(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { handleErrorMessage(it) }
                .subscribe()
                .disposeOnDestroy()
        }
    }

    override fun onStop() {
        super.onStop()
        rxErrorDisposable?.dispose()
    }


    override fun onDestroy() {
        disposables.clear()
        super.onDestroy()
    }

    protected fun Disposable.disposeOnDestroy(): Disposable {
        disposables.add(this)
        return disposables
    }
}