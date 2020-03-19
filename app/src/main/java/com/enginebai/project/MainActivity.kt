package com.enginebai.project

import android.os.Bundle
import com.enginebai.base.utils.RxErrorHandler
import com.enginebai.base.view.BaseActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import org.koin.android.ext.android.inject
import java.util.concurrent.TimeUnit

class MainActivity : BaseActivity() {

    private val rxErrorHandler: RxErrorHandler by inject()
    private var rxErrorDisposable: Disposable? = null

    override fun onStart() {
        super.onStart()
        if (null == rxErrorDisposable || false == rxErrorDisposable?.isDisposed) {
            rxErrorDisposable = rxErrorHandler.errorMessageToDisplay
                .filter { it.isNotBlank() }
                .throttleFirst(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    // TODO: display error message
                }
                .subscribe()
                .disposeOnDestroy()
        }
    }

    override fun getLayoutId() = R.layout.activity_main
}