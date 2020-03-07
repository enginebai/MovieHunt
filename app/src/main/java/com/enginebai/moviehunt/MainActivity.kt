package com.enginebai.moviehunt

import android.os.Bundle
import android.widget.Toast
import com.enginebai.base.view.BaseActivity
import com.enginebai.moviehunt.ui.movie.home.MovieHomeFragment
import com.enginebai.moviehunt.utils.RxErrorHandler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import org.koin.android.ext.android.inject
import java.util.concurrent.TimeUnit

class MainActivity : BaseActivity() {

    private val rxErrorHandler: RxErrorHandler by inject()
    private var rxErrorDisposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, MovieHomeFragment())
            .commit()
    }

    override fun onStart() {
        super.onStart()
        if (null == rxErrorDisposable || false == rxErrorDisposable?.isDisposed) {
            rxErrorDisposable = rxErrorHandler.errorMessageToDisplay
                .filter { it.isNotBlank() }
                .throttleFirst(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                }
                .subscribe()
                .disposeOnDestroy()
        }
    }

    override fun onStop() {
        super.onStop()
        rxErrorDisposable?.dispose()
    }

    override fun getLayoutId() = R.layout.activity_main
}
