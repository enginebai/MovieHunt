package com.enginebai.moviehunt.data.repo

interface NextPageIndex {
    fun setNextPageIndex(key: String, page: Int?)
    fun getNextPageIndex(key: String): Int?
    fun reset(key: String)
    fun clear()
}

class NextPageIndexImpl : NextPageIndex {

    private val nextPageMap = mutableMapOf<String, Int>()

    override fun setNextPageIndex(key: String, page: Int?) {
        page?.run {
            nextPageMap[key] = this
        } ?: run {
            reset(key)
        }
    }

    override fun getNextPageIndex(key: String): Int? = nextPageMap.getOrElse(key, { null })

    override fun reset(key: String) {
        nextPageMap.remove(key)
    }

    override fun clear() {
        nextPageMap.clear()
    }
}