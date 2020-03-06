package com.enginebai.moviehunt.ui.movie.list

import android.content.Context
import com.airbnb.epoxy.EpoxyAsyncUtil
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.enginebai.moviehunt.R
import com.enginebai.moviehunt.data.local.*
import com.enginebai.moviehunt.ui.movie.OnMovieClickListener

class MovieListController(
    private val context: Context,
    private val clickListener: OnMovieClickListener
) : PagedListEpoxyController<MovieModel>(diffingHandler = EpoxyAsyncUtil.getAsyncBackgroundHandler()) {

    // @AutoModel, This does not work with PagedListEpoxyController
    // source: https://github.com/airbnb/epoxy/wiki/Epoxy-Controller#automodels
    private val loadMoreView = LoadMoreView_().apply { id(LoadMoreView::class.java.simpleName) }
    var loadingMore = false
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildItemModel(currentPosition: Int, item: MovieModel?): EpoxyModel<*> {
        return item?.run {
            MovieListEpoxyModel_()
                .id(this.id)
                .movieId(this.id)
                .imagePoster(this.getPosterUrl())
                .textTitle(this.displayTitle())
                .rating(this.display5StarsRating())
                .voteCount(context.getString(R.string.vote_count, this.displayVoteCount()))
                .duration(this.displayDuration())
                .releaseDate(this.displayReleaseDate())
                .itemClickListener { clickListener.onMovieClicked(this.id) }
        } ?: run {
            MovieListEpoxyModel_()
                .id(-currentPosition)
        }
    }

    override fun addModels(models: List<EpoxyModel<*>>) {
        super.addModels(models)
        loadMoreView.addIf(loadingMore, this)
    }
}