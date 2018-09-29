package ru.spbstu.icc.kspt.storage

import android.app.Activity
import android.content.Intent
import ru.spbstu.icc.kspt.common.getExtra
import java.io.File
import java.util.concurrent.atomic.AtomicInteger

class ExternalStorageManager(private val activity: Activity) {

    private val callbacks = HashMap<Int, (File) -> Unit>()

    private val idGenerator = AtomicInteger()

    fun getFileForRead(template: Regex? = null, callback: (File) -> Unit) {
        getFile(StorageActivity.AccessType.READ, template, callback)
    }

    fun getFileForWrite(template: Regex? = null, callback: (File) -> Unit) {
        getFile(StorageActivity.AccessType.WRITE, template, callback)
    }

    private fun getFile(type: StorageActivity.AccessType, template: Regex?, callback: (File) -> Unit) {
        val id = idGenerator.incrementAndGet()
        callbacks[id] = callback
        val intent = Intent(activity, StorageActivity::class.java)
        intent.putExtra(CALLBACK_ID, id)
        intent.putExtra(StorageActivity.FILE_TEMPLATE, template)
        intent.putExtra(StorageActivity.ACCESS_TYPE, type)
        activity.setResult(Activity.RESULT_OK, intent)
        activity.startActivityForResult(intent, REQUEST_CODE)
    }

    fun onActivityResult(data: Intent) {
        val file = data.getExtra<File>(StorageActivity.FILE_RESULT)
        val id = data.getExtra<Int>(CALLBACK_ID)
        callbacks.remove(id)?.invoke(file)
    }

    companion object {
        const val CALLBACK_ID = "ru.spbstu.icc.kspt.storage.ExternalStorageManager.CALLBACK_ID"
        const val REQUEST_CODE = 80
    }
}