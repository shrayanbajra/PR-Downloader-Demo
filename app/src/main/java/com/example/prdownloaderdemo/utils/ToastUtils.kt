package com.example.prdownloaderdemo.utils

import android.widget.Toast

object ToastUtils {

    private var toast: Toast? = null

    fun show(message: String) {
        if (toast != null) {
            toast?.cancel()
        }
        toast = Toast.makeText(AppUtils.getApp(), message, Toast.LENGTH_SHORT)
        toast?.show()
    }
}