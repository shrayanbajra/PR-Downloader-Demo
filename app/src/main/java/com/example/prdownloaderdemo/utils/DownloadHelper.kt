package com.example.prdownloaderdemo.utils

import android.os.Environment
import android.util.Log
import com.downloader.Error
import com.downloader.OnDownloadListener
import com.downloader.PRDownloader
import com.downloader.Progress
import java.net.URI

object DownloadHelper {

    fun download(uri: String, onDownloadListener: OnDownload): Int {

        val fileName = getFilenameFromURI(uri)
        logFileStatus(fileName)

        return PRDownloader.download(uri, getDirPath(), fileName).build()
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
                    Log.d(
                        "DownloadHelper",
                        "ERROR: is Connection Error: ${error?.isConnectionError}"
                    )
                    Log.d("DownloadHelper", "ERROR: is Server Error: ${error?.isServerError}")
                    Log.d("DownloadHelper", "ERROR: Response Code: ${error?.responseCode}")
                    Log.d(
                        "DownloadHelper",
                        "ERROR: Server Error Message: ${error?.serverErrorMessage}"
                    )
                    Log.d("DownloadHelper", "onError()")
                }
            })
    }

    private const val DIRECTORY_NAME = "Downloads"

    private fun getDirPath(): String {
        return Environment.getExternalStorageDirectory().absolutePath + "/$DIRECTORY_NAME"
    }

    fun getFullPath(fileName: String = ""): String {
        return Environment.getExternalStorageDirectory().absolutePath + "/$DIRECTORY_NAME/" + fileName
    }

    private fun logFileStatus(fileName: String) {
        Log.d("DownloadHelper", "Filename -> $fileName")
        Log.d("DownloadHelper", "Download Location -> ${getFullPath(fileName)}")
    }

    private fun getFilenameFromURI(url: String): String {
        // because backslash is not a valid character in url
        val safeUrl = url.replace("\\", "/")
        return URI(safeUrl).path.substringAfterLast('/')
    }

    fun resume(id: Int) {
        PRDownloader.resume(id)
    }

    fun pause(id: Int) {
        PRDownloader.pause(id)
    }

    fun cancel(id: Int) {
        PRDownloader.cancel(id)
    }

    fun cancelAll() {
        PRDownloader.cancelAll()
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
