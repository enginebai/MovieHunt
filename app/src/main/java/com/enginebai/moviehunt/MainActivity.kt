package com.enginebai.moviehunt

import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import com.enginebai.base.view.BaseActivity
import com.enginebai.moviehunt.ui.home.MovieHomeFragment
import com.enginebai.moviehunt.utils.ExceptionHandler
import com.enginebai.moviehunt.utils.openFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import org.koin.android.ext.android.inject
import java.util.concurrent.TimeUnit

class MainActivity : BaseActivity() {

    private val exceptionHandler: ExceptionHandler by inject()
    private var exceptionHandlerDisposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        openFragment(MovieHomeFragment(), false)
    }

    override fun onStart() {
        super.onStart()
        // You will handle the error message at single activity.
        if (null == exceptionHandlerDisposable || false == exceptionHandlerDisposable?.isDisposed) {
            exceptionHandlerDisposable = exceptionHandler.errorMessageToDisplay
                    .filter { it.isNotBlank() }
                    .throttleFirst(2, TimeUnit.SECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext { handleErrorMessage(it) }
                    .subscribe()
        }
    }

    override fun onStop() {
        exceptionHandlerDisposable?.dispose()
        super.onStop()
    }

    override fun handleErrorMessage(message: String) {
        displayCustomToast(message)
    }

    override fun getLayoutId() = R.layout.activity_main

    private fun displayCustomToast(message: String) {
        if (message.isBlank()) return
        val layout = layoutInflater.inflate(R.layout.toast, null)
        val textView = layout.findViewById<TextView>(R.id.textToast)
        textView.text = message
        with(Toast(this)) {
            setGravity(Gravity.FILL_HORIZONTAL or Gravity.TOP, 0, 0)
            duration = Toast.LENGTH_SHORT
            view = layout
            show()
        }
    }
}