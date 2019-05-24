package com.newkirkj.seattlesearch

import android.content.Context
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule


/**
 * Created by Joshua Newkirk on 5/20/2019.
 */
@GlideModule
class SeattleSearchGlideModule : AppGlideModule() {

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        // Uncomment to see Glide requests in Logcat
//        if (BuildConfig.DEBUG) {
//            builder.setLogLevel(Log.DEBUG)
//        }
        super.applyOptions(context, builder)
    }
}