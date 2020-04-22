package com.example.prdownloaderdemo

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.downloader.Error
import com.downloader.Progress
import com.example.prdownloaderdemo.utils.DownloadHelper
import com.example.prdownloaderdemo.utils.ToastUtils

class MainActivity : AppCompatActivity() {

    private lateinit var etURI: EditText
    private lateinit var btnDownload: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etURI = findViewById(R.id.et_uri)
        btnDownload = findViewById(R.id.btn_download)

        btnDownload.setOnClickListener {

            val uri = getInputURI()

            if (uri.isBlank()) {
                ToastUtils.show("Please Enter URL")
                return@setOnClickListener
            }

            downloadFile(uri)
        }
    }

    private fun getInputURI() = etURI.text.toString().trim()

    private fun downloadFile(URI: String) {
        var downloader = DownloadHelper.download(URI, object : DownloadHelper.OnDownload {

            override fun onStartOrResume() {
                ToastUtils.show("Starting Download")
            }

            override fun onProgress(progress: Progress) {
                val downloadProgress = (progress.currentBytes / progress.totalBytes) * 100
                Log.d("MainActivity", "Download Progress $downloadProgress")
            }

            override fun onPause() {
                ToastUtils.show("Download Paused")
            }

            override fun onCancel() {
                ToastUtils.show("Download Paused")
            }

            override fun onComplete() {
                ToastUtils.show("Download Complete")
            }

            override fun onError(error: Error?) {
                ToastUtils.show("Error while downloading")
            }
        })
    }
}
