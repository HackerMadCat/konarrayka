package ru.spbstu.icc.kspt.storage

import android.app.Activity
import android.content.Intent
import ru.spbstu.icc.kspt.common.CallManager
import java.io.File

class ExternalStorageManager {

    private val suggestionExternalStorageCallManager = CallManager<File, SuggestionExternalStorageActivity>(
            SuggestionExternalStorageActivity.FILE_RESULT
    )

    private val manualExternalStorageCallManager = CallManager<File, ManualExternalStorageActivity>(
            ManualExternalStorageActivity.FILE_RESULT
    )

    fun getFileForRead(activity: Activity, template: Regex, callback: (File) -> Unit) {
        getFile(activity, StorageActivity.AccessType.READ, template, callback)
    }

    fun getFileForWrite(activity: Activity, template: Regex, callback: (File) -> Unit) {
        getFile(activity, StorageActivity.AccessType.WRITE, template, callback)
    }

    fun getFileForRead(activity: Activity, callback: (File) -> Unit) {
        getFile(activity, StorageActivity.AccessType.READ, callback)
    }

    fun getFileForWrite(activity: Activity, callback: (File) -> Unit) {
        getFile(activity, StorageActivity.AccessType.WRITE, callback)
    }

    private fun getFile(
            activity: Activity,
            type: StorageActivity.AccessType,
            callback: (File) -> Unit
    ) {
        manualExternalStorageCallManager.call(activity, MANUAL_EXTERNAL_STORAGE_REQUEST_CODE, callback) {
            putExtra(StorageActivity.ACCESS_TYPE, type)
        }
    }

    private fun getFile(
            activity: Activity,
            type: StorageActivity.AccessType,
            template: Regex,
            callback: (File) -> Unit
    ) {
        suggestionExternalStorageCallManager.call(activity, SUGGESTION_EXTERNAL_STORAGE_REQUEST_CODE, callback) {
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