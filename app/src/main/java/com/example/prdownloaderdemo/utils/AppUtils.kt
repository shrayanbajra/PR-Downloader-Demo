package com.example.prdownloaderdemo.utils

import android.app.Application

object AppUtils {

    private lateinit var app: Application

    fun init(application: Application) {
        if (!AppUtils::app.isInitialized)
            app = application
    }

    fun getApp(): Application {
        return app
    }
}