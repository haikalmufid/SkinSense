package com.capstone.skinsense.ui

import android.app.Activity
import android.app.AlertDialog
import android.view.LayoutInflater
import com.capstone.skinsense.R

class ProgressBarActivity(private val activity: Activity) {

    private var dialog: AlertDialog? = null

    fun showProgressBar() {
        if (dialog == null) {
            val builder = AlertDialog.Builder(activity)
            val inflater = LayoutInflater.from(activity)
            val view = inflater.inflate(R.layout.activity_progressbar, null)
            builder.setView(view)
            builder.setCancelable(false)
            dialog = builder.create()
        }
        dialog?.show()
    }

    fun hideProgressBar() {
        dialog?.dismiss()
    }
}