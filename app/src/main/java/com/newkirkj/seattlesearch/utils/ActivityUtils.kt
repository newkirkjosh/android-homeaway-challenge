package com.newkirkj.seattlesearch.utils

import android.app.Activity
import android.util.DisplayMetrics

/**
 * Created by Joshua Newkirk on 5/23/2019.
 */
fun Activity.screenWidth(): Int {
    val metrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(metrics)
    return metrics.widthPixels
}