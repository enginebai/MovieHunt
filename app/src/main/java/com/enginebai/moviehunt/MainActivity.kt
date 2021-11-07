package com.enginebai.moviehunt

import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import com.enginebai.base.view.BaseActivity
import com.enginebai.base.view.BaseViewModel
import com.enginebai.moviehunt.data.repo.ConfigRepo
import com.enginebai.moviehunt.ui.home.MovieHomeFragment
import com.enginebai.moviehunt.ui.home.SplashFragment
import com.enginebai.moviehunt.utils.ExceptionHandler
import com.enginebai.moviehunt.utils.openFragment
import com.google.accompanist.appcompattheme.AppCompatTheme
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.SerialDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.inject
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
            AppCompatTheme {
                Greeting()
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

@ExperimentalUnitApi
@Composable
@Preview
private fun Greeting() {
    Column(
        modifier = Modifier
            .padding(
                horizontal = dimensionResource(id = R.dimen.page_horizontal_padding),
                vertical = dimensionResource(id = R.dimen.size_12)
            )
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            style = TextStyle(
                color = Color.White,
                fontSize = TextUnit(dimensionResource(id = R.dimen.text_20).value, TextUnitType.Sp),
                fontWeight = FontWeight.Bold
            ),
        )
    }
}