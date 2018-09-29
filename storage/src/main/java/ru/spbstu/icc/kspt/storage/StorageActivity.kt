package ru.spbstu.icc.kspt.storage

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.EditText
import java.io.File
import ru.spbstu.icc.kspt.common.alert
import ru.spbstu.icc.kspt.common.getExtra


class StorageActivity : AppCompatActivity() {

    private var onGetPermissionsAction: ((Boolean) -> Unit)? = null

    private val template by lazy { getExtra<Regex>(FILE_TEMPLATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_storage)
//        val files = android.os.Environment.getExternalStorageDirectory().walk()
//                .filter { it.isFile }
//                .filter { template.matches(it.name) }
    }

    fun returnAction(@Suppress("UNUSED_PARAMETER") view: View) {
        if (onGetPermissionsAction != null) return
        val file = getFile() ?: return
        onGetPermissionsAction = { permissionGranted ->
            onGetPermissionsAction = null
            if (permissionGranted) {
                intent.putExtra(FILE_RESULT, file)
                setResult(Activity.RESULT_OK, intent)
                finish()
            } else {
                alert(getString(R.string.permissionDenied))
            }
        }
        when (getExtra<AccessType>(ACCESS_TYPE)) {
            AccessType.READ -> getReadPermission()
            AccessType.WRITE -> getWritePermission()
        }
    }

    private fun getFile(): File? {
        val editText = findViewById<EditText>(R.id.pathEdit)
        val path = editText.text.toString()
        val file = File(path)
        if (!template.matches(file.name)) {
            alert("File name not matches by regex $template")
            return null
        }
        if (!file.exists()) {
            alert("File does not exists")
            return null
        }
        if (!file.isFile) {
            alert("File is not a file")
            return null
        }
        return file
    }

    private fun getWritePermission() {
        getPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, WRITE_PERMISSIONS_REQUEST)
    }

    private fun getReadPermission() {
        getPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, READ_PERMISSIONS_REQUEST)
    }

    private fun getPermissions(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
            onGetPermissionsAction!!(true)
            return
        }
        ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            READ_PERMISSIONS_REQUEST, WRITE_PERMISSIONS_REQUEST -> {
                val permissionGranted = grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
                onGetPermissionsAction!!(permissionGranted)
                return
            }
        }
    }

    enum class AccessType { READ, WRITE }

    companion object {
        const val FILE_TEMPLATE = "ru.spbstu.icc.kspt.storage.StorageActivity.FILE_EXTENSION"
        const val FILE_RESULT = "ru.spbstu.icc.kspt.storage.StorageActivity.FILE_RESULT"
        const val ACCESS_TYPE = "ru.spbstu.icc.kspt.storage.StorageActivity.ACCESS_TYPE"
        const val READ_PERMISSIONS_REQUEST = 4005
        const val WRITE_PERMISSIONS_REQUEST = 2005
    }
}
