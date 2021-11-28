package com.enginebai.moviehunt

import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.unit.ExperimentalUnitApi
import com.enginebai.base.view.BaseActivity
import com.enginebai.base.view.BaseViewModel
import com.enginebai.moviehunt.data.repo.ConfigRepo
import com.enginebai.moviehunt.resources.MovieHuntTheme
import com.enginebai.moviehunt.ui.detail.holders.MovieInfoWidget
import com.enginebai.moviehunt.ui.home.MovieHomeFragment
import com.enginebai.moviehunt.ui.home.SplashFragment
import com.enginebai.moviehunt.ui.widgets.MovieReviewWidget
import com.enginebai.moviehunt.utils.DateTimeFormatter.format
import com.enginebai.moviehunt.utils.ExceptionHandler
import com.enginebai.moviehunt.utils.openFragment
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.SerialDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.inject
import java.util.*
import java.util.concurrent.TimeUnit

@ExperimentalUnitApi
class MainActivity : BaseActivity() {

    private val mainViewModel by viewModel<MainViewModel>()
    private val exceptionHandler: ExceptionHandler by inject()
    private var exceptionHandlerDisposable = SerialDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel.fetchGenreList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                openFragment(SplashFragment(), false)
            }
            .doOnComplete {
                openFragment(MovieHomeFragment(), false)
            }
            .doOnError {
                it.printStackTrace()
            }
            .subscribe()
            .disposeOnDestroy()
        composeView.setContent {
            MovieHuntTheme {
                Column {
                    MovieInfoWidget(
                        posterUrl = "https://image.tmdb.org/t/p/w780//or06FN3Dka5tukK1e9sl16pB3iy.jpg",
                        movieName = "Star Wars",
                        isBookmark = false
                    )
                    MovieReviewWidget(
                        movieId = "1234",
                        name = "Robert",
                        avatar = "https://image.tmdb.org/t/p/w500/4DiJQ1mBp4ztoznZADIrPg69v46.jpg",
                        createdAtDateText = Calendar.getInstance().format(),
                        rating = 9.5f,
                        comment = "The character development for Thanos was so good that it made me think that maybe he was right. He was the villain that surpassed all the other villains from the past Marvel movies. Trust me, this is the movie that might have changed the MCU."
                    )
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        // You will handle the error message at single activity.
        exceptionHandlerDisposable.set(exceptionHandler.errorMessageToDisplay
            .filter { it.isNotBlank() }
            .throttleFirst(2, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { handleErrorMessage(it) }
            .subscribe())
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

class MainViewModel : BaseViewModel() {
    private val configRepo by inject<ConfigRepo>()

    fun fetchGenreList(): Completable {
        return configRepo.fetchGenreList().ignoreElement()
    }
}