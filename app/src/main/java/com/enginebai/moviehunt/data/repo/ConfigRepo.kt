package com.enginebai.moviehunt.data.repo

import com.enginebai.moviehunt.data.remote.Genre
import com.enginebai.moviehunt.data.remote.MovieApiService
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface ConfigRepo {
    val genreList: BehaviorSubject<List<Genre>>
    fun fetchGenreList(): Single<List<Genre>>
}

class ConfigRepoImpl : ConfigRepo, KoinComponent {

    private val movieApi by inject<MovieApiService>()

    override val genreList = BehaviorSubject.create<List<Genre>>()

    override fun fetchGenreList(): Single<List<Genre>> {
        return Single.create<List<Genre>> {
            if (genreList.value?.isNotEmpty() == true) {
                it.onSuccess(genreList.value!!)
            } else {
                it.onError(NoSuchElementException())
            }
        }.onErrorResumeNext {
            movieApi.fetchGenreList().map { it.genreList ?: throw NullPointerException() }
                    .doOnSuccess {
                        genreList.onNext(it)
                    }
        }
    }

}