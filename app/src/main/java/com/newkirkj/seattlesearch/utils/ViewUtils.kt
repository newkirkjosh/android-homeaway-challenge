package com.newkirkj.seattlesearch.utils

import android.view.View
import androidx.annotation.IdRes

/**
 * Created by Joshua Newkirk on 5/20/2019.
 */

fun <T : View> View.bind(@IdRes res: Int): Lazy<T> {
    return lazy(LazyThreadSafetyMode.NONE) { findViewById<T>(res) }
}