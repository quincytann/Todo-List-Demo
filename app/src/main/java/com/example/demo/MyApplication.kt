package com.example.demo

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/**
 * Created by tanqing.cc
 * on 2021/7/9
 */

class MyApplication : Application() {
    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}
