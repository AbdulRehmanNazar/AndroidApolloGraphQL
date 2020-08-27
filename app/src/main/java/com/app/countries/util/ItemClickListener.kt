package com.app.countries.util

/**
 * A generic interface to give callBack when item of RecyclerView clicked
 * @param <T>
 */
interface ItemClickListener<T> {
    fun onClick(data: T)
}