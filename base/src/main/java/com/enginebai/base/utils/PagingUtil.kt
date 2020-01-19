package com.enginebai.base.utils

import androidx.paging.PagedList
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

enum class NetworkState {
    IDLE,
    LOADING,
    ERROR
}

/**
 * It is used for UI to show a list and corresponding states and actions.
 */
data class Listing<T>(
    // the paged list for UI to observe
    val pagedList: Observable<PagedList<T>>,
    // the network request state for pull-to-refresh or first time refresh
    val refreshState: BehaviorSubject<NetworkState>? = null,
    // the network request state to show progress or error
    val loadMoreState: BehaviorSubject<NetworkState>? = null
    )