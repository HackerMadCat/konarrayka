package ru.spbstu.icc.kspt.common

import android.app.Activity
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import java.lang.IllegalArgumentException
import java.util.concurrent.atomic.AtomicInteger


class PermissionManager {

    private val callbacks = HashMap<Int, (Boolean) -> Unit>()

    private val idGenerator = AtomicInteger()

    fun onRequestPermissionsResult(
            requestCode: Int,
            @Suppress("UNUSED_PARAMETER") permissions: Array<String>,
            grantResults: IntArray
    ) {
        val permissionGranted = grantResults.all { it == PackageManager.PERMISSION_GRANTED }
        val callback = callbacks.remove(requestCode)
                ?: throw IllegalArgumentException("Permission request $requestCode was not produced by $this")
        callback(permissionGranted)
    }

    fun withPermissions(activity: Activity, vararg permissions: String, callback: (Boolean) -> Unit) {
        val id = idGenerator.incrementAndGet()
        callbacks[id] = { permissionGranted ->
            callback(permissionGranted)
        }
        requestPermissions(id, activity, permissions)
    }

    private fun requestPermissions(id: Int, activity: Activity, permissions: Array<out String>) {
        val allPermissionGranted = permissions.all {
            ContextCompat.checkSelfPermission(activity, it) == PackageManager.PERMISSION_GRANTED
        }
        if (allPermissionGranted) {
            val callback = callbacks[id]!!
            callback(true)
            return
        }
        ActivityCompat.requestPermissions(activity, permissions, id)
    }
}
