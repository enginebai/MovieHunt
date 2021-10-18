package com.enginebai.moviehunt.ui.list

import com.airbnb.epoxy.EpoxyAsyncUtil
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.enginebai.moviehunt.data.local.*
import com.enginebai.moviehunt.ui.MovieClickListener
import com.enginebai.moviehunt.ui.holders.MovieLandscapeHolder_
import com.enginebai.moviehunt.utils.DateTimeFormatter.format

class MovieLandscapeController(
    private val clickListener: MovieClickListener
) : PagedListEpoxyController<MovieModel>(diffingHandler = EpoxyAsyncUtil.getAsyncBackgroundHandler()) {

    private val loadMoreView = LoadMoreView_().apply { id(LoadMoreView::class.java.simpleName) }
    var loadingMore = false
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildItemModel(currentPosition: Int, item: MovieModel?): EpoxyModel<*> {
        return item?.run {
            MovieLandscapeHolder_()
                .id(this.id)
                .movieId(this.id)
                .imagePoster(this.getPosterUrl())
                .textTitle(this.displayTitle())
                .rating(this.display5StarsRating())
                .ratingTotalCountText(this.displayVoteCount())
                .genre(this.genreList?.map { it.name }?.joinToString())
                .releaseDateText(this.releaseDate?.format())
                .itemClickListener { clickListener.onMovieClicked(this.id) }
        } ?: run {
            MovieLandscapeHolder_()
                .id(-currentPosition)
        }
    }

    override fun addModels(models: List<EpoxyModel<*>>) {
        super.addModels(models)
        loadMoreView.addIf(loadingMore, this)
    }
}