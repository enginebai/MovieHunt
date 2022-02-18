package com.enginebai.moviehunt.ui.list

import com.airbnb.epoxy.EpoxyAsyncUtil
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.enginebai.moviehunt.data.local.MovieModel
import com.enginebai.moviehunt.data.local.display5StarsRating
import com.enginebai.moviehunt.data.local.displayTitle
import com.enginebai.moviehunt.data.local.displayVoteCount
import com.enginebai.moviehunt.data.remote.ImageApi
import com.enginebai.moviehunt.data.remote.ImageSize
import com.enginebai.moviehunt.ui.MovieClickListener
import com.enginebai.moviehunt.ui.widgets.MovieLandscapeHolder_
import com.enginebai.moviehunt.utils.DateTimeFormatter.format

@Deprecated("To remove after post writing.")
class MovieLandscapeController(
    private val clickListener: MovieClickListener
) : PagingDataEpoxyController<MovieModel>(diffingHandler = EpoxyAsyncUtil.getAsyncBackgroundHandler()) {

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
                .imagePoster(ImageApi.getFullUrl(this.posterPath, ImageSize.W500))
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