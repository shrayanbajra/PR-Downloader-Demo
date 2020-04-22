package com.example.prdownloaderdemo

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var etURI: EditText
    private lateinit var btnDownload: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etURI = findViewById(R.id.et_uri)
        btnDownload = findViewById(R.id.btn_download)

        btnDownload.setOnClickListener {
            // Start Downloading Here
        }
    }
}
