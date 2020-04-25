package com.example.prdownloaderdemo

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.downloader.Error
import com.downloader.Progress
import com.example.prdownloaderdemo.utils.DownloadHelper
import com.example.prdownloaderdemo.utils.ToastUtils

class MainActivity : AppCompatActivity() {

    private lateinit var etURI: EditText

    private lateinit var btnDownload: Button

    private lateinit var btnResume: Button
    private lateinit var btnPause: Button
    private lateinit var btnCancel: Button

    private lateinit var tvProgressBarDetails: TextView
    private lateinit var progressBar: ProgressBar

    private var downloadID: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()

        btnDownloadListener()

        btnResumeListener()
        btnPauseListener()
        btnCancelListener()
    }

    private fun initViews() {
        etURI = findViewById(R.id.et_uri)

        btnDownload = findViewById(R.id.btn_download)

        btnResume = findViewById(R.id.btn_resume)
        btnPause = findViewById(R.id.btn_pause)
        btnCancel = findViewById(R.id.btn_cancel)

        tvProgressBarDetails = findViewById(R.id.tv_progress_bar_detail)
        progressBar = findViewById(R.id.progress_bar)
    }

    private fun btnDownloadListener() {
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
        downloadID = DownloadHelper.download(URI, object : DownloadHelper.OnDownload {

            override fun onStartOrResume() {
                ToastUtils.show("Starting Download")
                showProgress()
            }

            override fun onProgress(progress: Progress) {
                val downloadProgress = (progress.currentBytes * 100.0) / progress.totalBytes
                progressBar.progress = downloadProgress.toInt()
                val progressDetail = "Completed: ${downloadProgress.toInt()}%"
                tvProgressBarDetails.text = progressDetail
                Log.d("MainActivity", "Download Progress $downloadProgress")
            }

            override fun onPause() {
                ToastUtils.show("Download Paused")
            }

            override fun onCancel() {
                ToastUtils.show("Download Cancelled")
                hideProgress()
            }

            override fun onComplete() {
                ToastUtils.show("Download Complete")
                hideProgress()
            }

            override fun onError(error: Error?) {
                Log.d("MainActivity", "ERROR: is Connection Error: ${error?.isConnectionError}")
                Log.d("MainActivity", "ERROR: is Server Error: ${error?.isServerError}")
                Log.d("MainActivity", "ERROR: Response Code: ${error?.responseCode}")
                Log.d("MainActivity", "ERROR: Server Error Message: ${error?.serverErrorMessage}")
                ToastUtils.show("Error while downloading")
            }
        })
    }

    private fun btnResumeListener() {
        btnResume.setOnClickListener {
            DownloadHelper.resume(downloadID)
        }
    }

    private fun btnPauseListener() {
        btnPause.setOnClickListener {
            DownloadHelper.pause(downloadID)
        }
    }

    private fun btnCancelListener() {
        btnCancel.setOnClickListener {
            DownloadHelper.cancel(downloadID)
        }
    }

    private fun showProgress() {
        progressBar.visibility = View.VISIBLE
        tvProgressBarDetails.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        progressBar.visibility = View.GONE
        tvProgressBarDetails.visibility = View.GONE
    }
}
