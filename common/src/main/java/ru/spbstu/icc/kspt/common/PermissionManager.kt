package ru.spbstu.icc.kspt.common

import android.app.Activity
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat


class PermissionManager(private val activity: Activity) {

    private var onGetPermissionsAction: ((Boolean) -> Unit)? = null

    @Suppress("UNUSED_PARAMETER")
    fun onRequestPermissionsResult(permissions: Array<String>, grantResults: IntArray) {
        val permissionGranted = grantResults.all { it == PackageManager.PERMISSION_GRANTED }
        onGetPermissionsAction!!(permissionGranted)
    }

    fun withPermissions(vararg permissions: String, apply: (Boolean) -> Unit) {
        onGetPermissionsAction = { permissionGranted ->
            onGetPermissionsAction = null
            apply(permissionGranted)
        }
        requestPermissions(permissions)
    }

    private fun requestPermissions(permissions: Array<out String>) {
        val allPermissionGranted = permissions.all {
            ContextCompat.checkSelfPermission(activity, it) == PackageManager.PERMISSION_GRANTED
        }
        if (allPermissionGranted) {
            onGetPermissionsAction!!(true)
            return
        }
        ActivityCompat.requestPermissions(activity, permissions, PERMISSION_REQUEST)
    }

    companion object {
        const val PERMISSION_REQUEST = 948
    }
}
