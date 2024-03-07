package com.example.exchangerates2024.presentation.dialogs

import android.app.AlertDialog
import android.content.Context
import android.view.Gravity
import android.widget.TextView
import com.example.exchangerates2024.R

class BasicAlertDialog {
    fun getDialog(context: Context, title: String, message: String): AlertDialog {
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setTitle(title)

        alertDialogBuilder.setMessage(message)

        alertDialogBuilder.setPositiveButton(context.getString(R.string.ok)) { dialog, _ ->
            dialog.dismiss()
        }
        val alertDialog = alertDialogBuilder.show()
        alertDialog.findViewById<TextView>(android.R.id.message).gravity = Gravity.CENTER
        return alertDialog
    }
}