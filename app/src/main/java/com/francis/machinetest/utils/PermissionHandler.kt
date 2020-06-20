package com.francis.machinetest.utils

import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.francis.machinetest.base.AppController

object PermissionHandler {

    fun checkPermissionGrandedOrNot(permissionName: String) =
        if (ContextCompat.checkSelfPermission(AppController.instance, permissionName)
            == PackageManager.PERMISSION_GRANTED
        )
            true else false


}