package com.enginebai.moviehunt.ui.reviews

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.enginebai.moviehunt.data.remote.Review
import com.enginebai.moviehunt.ui.widgets.MovieReviewHolder_
import com.enginebai.moviehunt.ui.widgets.toHolder

class MovieReviewsController : PagingDataEpoxyController<Review>() {
    override fun buildItemModel(currentPosition: Int, item: Review?): EpoxyModel<*> {
        return item?.toHolder() ?: let {
            MovieReviewHolder_()
                .id(currentPosition)
        }
    }
}