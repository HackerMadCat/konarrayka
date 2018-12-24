package ru.spbstu.icc.kspt.storage

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import ru.spbstu.icc.kspt.common.toast
import java.io.File


class ManualExternalStorageActivity : StorageActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manual_external_storage)
    }

    fun returnAction(@Suppress("UNUSED_PARAMETER") view: View) = withPermissions {
        val file = getFile() ?: return@withPermissions
        intent.putExtra(RESULT, file)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun getFile(): File? {
        val editText = findViewById<EditText>(R.id.pathEdit)
        val path = editText.text.toString()
        val file = File(path)
        if (!file.exists()) {
            toast("file not exists")
            return null
        }
        if (!file.isFile) {
            toast("invalid file")
            return null
        }
        return file
    }

    companion object {
        const val RESULT = "ru.spbstu.icc.kspt.storage.ManualExternalStorageActivity.RESULT"
    }
}
