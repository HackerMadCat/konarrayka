package ru.spbstu.icc.kspt.storage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import ru.spbstu.icc.kspt.common.CallManager

import ru.spbstu.icc.kspt.common.alert
import ru.spbstu.icc.kspt.common.getExtra
import java.io.File
import android.widget.ArrayAdapter
import kotlin.concurrent.thread


class SuggestionExternalStorageActivity : StorageActivity() {

    private val callManager = CallManager<File, ManualExternalStorageActivity>()

    private val template by lazy { getExtra<Regex>(FILE_TEMPLATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_suggestion_external_storage)
        configureListView()
    }

    private fun configureListView() {
        val mutex = Any()
        val fileList = ArrayList<File>()
        val pathList = ArrayList<String>()
        val pathView = findViewById<ListView>(R.id.pathView)
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, pathList)
        pathView.adapter = adapter
        pathView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val file = synchronized(mutex) {
                fileList[position]
            }
            withPermissions { permissionGranted ->
                if (permissionGranted) {
                    returnFile(file)
                } else {
                    alert(getString(R.string.permissionDenied))
                }
            }
        }
        val externalStoragePath = getString(R.string.externalStoragePath)
        thread {
            android.os.Environment.getExternalStorageDirectory()
                    .walk().asSequence()
                    .filter { it.isFile }
                    .filter { template.matches(it.name) }
                    .forEach { file ->
                        val path = file.absolutePath.removePrefix(externalStoragePath)
                        synchronized(mutex) {
                            fileList.add(file)
                            runOnUiThread {
                                adapter.add(path)
                            }
                        }
                    }
        }
    }

    fun manualAction(@Suppress("UNUSED_PARAMETER") view: View) {
        callManager.call(this, REQUEST_CODE, ::returnFile) {
            putExtra(StorageActivity.ACCESS_TYPE, accessType)
        }
    }

    private fun returnFile(file: File) {
        intent.putExtra(RESULT, file)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK) return
        if (data == null) return
        when (requestCode) {
            REQUEST_CODE -> callManager.onActivityResult(data)
        }
    }

    companion object {
        const val FILE_TEMPLATE = "ru.spbstu.icc.kspt.storage.SuggestionExternalStorageActivity.FILE_TEMPLATE"
        const val RESULT = "ru.spbstu.icc.kspt.storage.SuggestionExternalStorageActivity.RESULT"
        const val REQUEST_CODE = 80
    }
}
