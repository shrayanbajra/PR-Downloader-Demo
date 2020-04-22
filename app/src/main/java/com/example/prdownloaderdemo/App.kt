package com.example.prdownloaderdemo

import android.app.Application
import com.downloader.PRDownloader
import com.downloader.PRDownloaderConfig

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        // Download Helper
        initPRDownloader()

    }

    private fun initPRDownloader() {
        val prDownloaderConfig = PRDownloaderConfig.newBuilder()
            .setDatabaseEnabled(true)
            .setReadTimeout(30_000)
            .setConnectTimeout(30_000)
            .build()
        PRDownloader.initialize(this, prDownloaderConfig)
    }
}