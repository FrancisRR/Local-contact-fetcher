package com.francis.machinetest.utils

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.francis.machinetest.base.AppController

object UiUtils {

    internal fun appErrorLog(tag: String?, message: String?) {
        Log.e("$tag", "$message")
    }

    internal fun appLog(tag: String?, message: String?) {
        Log.v("$tag", "$message")
    }

    internal fun showToast(message: String?) {
        if (message != null) {
            Toast.makeText(AppController.instance, "$message", Toast.LENGTH_SHORT).show()
        }
    }

    internal fun startPhoneCall(context: Context, number: String) {
        val callIntent = Intent(Intent.ACTION_CALL)
        callIntent.data = Uri.parse("tel:$number")

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        context.startActivity(callIntent)
    }


}