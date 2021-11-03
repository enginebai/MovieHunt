package com.enginebai.moviehunt.ui.reviews

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.enginebai.moviehunt.data.remote.Review
import com.enginebai.moviehunt.ui.holders.MovieReviewHolder_
import com.enginebai.moviehunt.ui.holders.toHolder

class MovieReviewsController : PagedListEpoxyController<Review>() {
    override fun buildItemModel(currentPosition: Int, item: Review?): EpoxyModel<*> {
        return item?.toHolder() ?: let {
            MovieReviewHolder_()
                .id(currentPosition)
        }
    }
}