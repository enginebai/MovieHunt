package com.enginebai.moviehunt

import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import com.enginebai.base.utils.RxErrorHandler
import com.enginebai.base.view.BaseActivity
import com.enginebai.moviehunt.ui.home.MovieHomeFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import org.koin.android.ext.android.inject
import java.util.concurrent.TimeUnit

class MainActivity : BaseActivity() {

    private val rxErrorHandler: RxErrorHandler by inject()
    private var rxErrorDisposable: Disposable? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		NavigationRouter.navigateToHome(this)
	}

	override fun onStart() {
        super.onStart()
        if (null == rxErrorDisposable || false == rxErrorDisposable?.isDisposed) {
            rxErrorDisposable = rxErrorHandler.errorMessageToDisplay
                .filter { it.isNotBlank() }
                .throttleFirst(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { displayCustomToast(it) }
                .subscribe()
                .disposeOnDestroy()
        }
    }

	override fun onStop() {
		super.onStop()
		rxErrorDisposable?.dispose()
	}

	override fun getLayoutId() = R.layout.activity_main

	private fun displayCustomToast(message: String) {
		if (message.isBlank()) return
		val layout = layoutInflater.inflate(R.layout.toast, null)
		val textView = layout.findViewById<TextView>(R.id.textToast)
		textView.text = message
		with (Toast(this)) {
			setGravity(Gravity.FILL_HORIZONTAL or Gravity.TOP, 0, 0)
			duration = Toast.LENGTH_SHORT
			view = layout
			show()
		}
	}
}