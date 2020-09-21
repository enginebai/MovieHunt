package com.enginebai.base.utils

import androidx.paging.PagedList
import io.reactivex.Observable

enum class NetworkState {
    IDLE,
    LOADING,
    ERROR
}

data class Listing<T>(
    // the paged list for UI to observer
    val pagedList: Observable<PagedList<T>>,
    // the network request status for pull-to-refresh or first time refresh
    val refreshState: Observable<NetworkState>? = null,
    // the network request state to show load more progress or error
    val loadMoreState: Observable<NetworkState>? = null,
    // refresh the whole data set and fetch it from scratch
    val refresh: () -> Unit = {}
)