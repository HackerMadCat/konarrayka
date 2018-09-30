package ru.spbstu.icc.kspt.storage

import android.app.Activity
import android.content.Intent
import ru.spbstu.icc.kspt.common.CallManager
import java.io.File

class ExternalStorageManager(activity: Activity) {

    private val suggestionExternalStorageCallManager = CallManager<File>(
            name = SuggestionExternalStorageActivity.FILE_RESULT,
            activityToCall = SuggestionExternalStorageActivity::class.java,
            activity = activity
    )

    private val manualExternalStorageCallManager = CallManager<File>(
            name = ManualExternalStorageActivity.FILE_RESULT,
            activityToCall = ManualExternalStorageActivity::class.java,
            activity = activity
    )

    fun getFileForRead(template: Regex, callback: (File) -> Unit) {
        getFile(StorageActivity.AccessType.READ, template, callback)
    }

    fun getFileForWrite(template: Regex, callback: (File) -> Unit) {
        getFile(StorageActivity.AccessType.WRITE, template, callback)
    }

    fun getFileForRead(callback: (File) -> Unit) {
        getFile(StorageActivity.AccessType.READ, callback)
    }

    fun getFileForWrite(callback: (File) -> Unit) {
        getFile(StorageActivity.AccessType.WRITE, callback)
    }

    private fun getFile(type: StorageActivity.AccessType, callback: (File) -> Unit) {
        manualExternalStorageCallManager.call(MANUAL_EXTERNAL_STORAGE_REQUEST_CODE, callback) {
            putExtra(StorageActivity.ACCESS_TYPE, type)
        }
    }

    private fun getFile(type: StorageActivity.AccessType, template: Regex, callback: (File) -> Unit) {
        suggestionExternalStorageCallManager.call(SUGGESTION_EXTERNAL_STORAGE_REQUEST_CODE, callback) {
            putExtra(SuggestionExternalStorageActivity.FILE_TEMPLATE, template)
            putExtra(StorageActivity.ACCESS_TYPE, type)
        }
    }

    fun onSuggestionExternalStorageActivityResult(data: Intent) =
            suggestionExternalStorageCallManager.onActivityResult(data)

    fun onManualExternalStorageActivityResult(data: Intent) =
            manualExternalStorageCallManager.onActivityResult(data)

    companion object {
        const val SUGGESTION_EXTERNAL_STORAGE_REQUEST_CODE = 81
        const val MANUAL_EXTERNAL_STORAGE_REQUEST_CODE = 82
    }
}