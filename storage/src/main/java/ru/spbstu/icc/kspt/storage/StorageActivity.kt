package ru.spbstu.icc.kspt.storage

import android.Manifest
import android.support.v7.app.AppCompatActivity
import ru.spbstu.icc.kspt.common.PermissionManager
import ru.spbstu.icc.kspt.common.getExtra

abstract class StorageActivity : AppCompatActivity() {

    @Suppress("LeakingThis")
    private val permissionManager = PermissionManager(this)

    val accessType by lazy {
        getExtra<AccessType>(ACCESS_TYPE)
    }

    fun withPermissions(apply: (Boolean) -> Unit) {
        val permission = when (accessType) {
            AccessType.READ -> Manifest.permission.READ_EXTERNAL_STORAGE
            AccessType.WRITE -> Manifest.permission.WRITE_EXTERNAL_STORAGE
        }
        permissionManager.withPermissions(permission, apply = apply)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PermissionManager.PERMISSION_REQUEST -> {
                permissionManager.onRequestPermissionsResult(permissions, grantResults)
                return
            }
        }
    }

    enum class AccessType { READ, WRITE }

    companion object {
        const val ACCESS_TYPE = "ru.spbstu.icc.kspt.storage.ManualExternalStorageActivity.ACCESS_TYPE"
    }
}