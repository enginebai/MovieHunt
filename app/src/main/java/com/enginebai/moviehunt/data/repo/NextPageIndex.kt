package com.enginebai.moviehunt.data.repo

interface NextPageIndex {
    fun setNextPageIndex(key: String, page: Int)
    fun getNextPageIndex(key: String): Int
    fun reset(key: String)
    fun clear()
}

class IncrementalNextPage : NextPageIndex {

    private val nextPageMap = mutableMapOf<String, Int>()

    override fun setNextPageIndex(key: String, page: Int) {
        nextPageMap[key] = page
    }

    override fun getNextPageIndex(key: String): Int {
        return nextPageMap.getOrDefault(key, 1)
    }

    override fun reset(key: String) {
        nextPageMap.remove(key)
    }

    override fun clear() {
        nextPageMap.clear()
    }

}