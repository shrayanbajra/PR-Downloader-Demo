package com.example.prdownloaderdemo

import android.os.Environment
import android.util.Log
import com.downloader.Error
import com.downloader.OnDownloadListener
import com.downloader.PRDownloader
import com.downloader.Progress
import java.net.URI

object DownloadHelper {

    fun getDownloadLocation(fileName: String = ""): String {
        return Environment.getExternalStorageDirectory().absolutePath + "/Downloads/" + fileName
    }

    fun download(url: String, onDownloadListener: OnDownload): Int {

        // because the backslash is not a valid character in url
        val safeUrl = url.replace("\\", "/")

        val fileName = URI(safeUrl).path.substringAfterLast('/')

        return PRDownloader.download(
            url,
            Environment.getExternalStorageDirectory().absolutePath + "/Downloads",
            fileName
        ).build()
            .setOnStartOrResumeListener {
                onDownloadListener.onStartOrResume()
                Log.d("DownloadHelper", "setOnStartOrResumeListener()")
            }
            .setOnProgressListener {
                onDownloadListener.onProgress(it)
                Log.d("DownloadHelper", "setOnProgressListener() $it")
            }
            .setOnCancelListener {
                onDownloadListener.onCancel()
                Log.d("DownloadHelper", "setOnCancelListener()")
            }
            .setOnPauseListener {
                onDownloadListener.onPause()
                Log.d("DownloadHelper", "setOnPauseListener()")
            }
            .start(object : OnDownloadListener {
                override fun onDownloadComplete() {
                    onDownloadListener.onComplete()
                    Log.d("DownloadHelper", "onDownloadComplete()")
                }

                override fun onError(error: Error?) {
                    onDownloadListener.onError(error)
                    Log.d("DownloadHelper", "onError()")
                }
            })
    }

    fun resume(id: Int) {
        PRDownloader.resume(id)
    }

    fun pause(id: Int) {
        PRDownloader.pause(id)
    }

    interface OnDownload {
        fun onStartOrResume()
        fun onProgress(progress: Progress)
        fun onPause()
        fun onCancel()
        fun onComplete()
        fun onError(error: Error?)
    }

}
