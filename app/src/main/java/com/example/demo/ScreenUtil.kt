package com.example.demo

import android.content.res.Resources
import android.util.DisplayMetrics


/**
 * Created by tanqing.cc
 * on 2021/7/9
 */

object ScreenUtil {

    fun getScreenWidth(): Int {
        val resources: Resources = MyApplication.context.resources
        val dm: DisplayMetrics = resources.displayMetrics
        val density = dm.density
        val width = dm.widthPixels
        val height = dm.heightPixels
        return width
    }


}