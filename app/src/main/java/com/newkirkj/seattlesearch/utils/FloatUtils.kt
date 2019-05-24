package com.newkirkj.seattlesearch.utils

import android.content.Context
import android.util.DisplayMetrics

/**
 * Created by Joshua Newkirk on 5/23/2019.
 */

fun Float.pixelToDp(context: Context): Int {
    val metrics = context.resources.displayMetrics
    return (this * (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)).toInt()
}