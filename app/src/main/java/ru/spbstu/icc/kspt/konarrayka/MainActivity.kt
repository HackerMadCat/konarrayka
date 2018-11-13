package ru.spbstu.icc.kspt.konarrayka

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import ru.spbstu.icc.kspt.common.toast
import ru.spbstu.icc.kspt.configuration.ConfigurationManager
import ru.spbstu.icc.kspt.storage.ExternalStorageManager
import java.io.File
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {

    private val externalStorageManager = ExternalStorageManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val logView = findViewById<TextView>(R.id.logView)
        logView.text = generateText()
    }

    private fun generateText() = StringBuilder().run {
        appendln("hello world!")
        val configurationManager = ConfigurationManager()
        val file = File("unused.file")
        val model = configurationManager.loadModel(file)
        val scenario = configurationManager.getScenario(model)
        for (action in scenario.getActions()) {
            appendln("-----------------")
            appendln(action.getRoles().joinToString(" and ") { it.getName() })
            appendln(action.getText())
        }
        toString()
    }

    fun findAction(@Suppress("UNUSED_PARAMETER") view: View) {
        val template = ".*\\.(txt|py|log|cfg|c|cpp|h|kt|java|js|css|html|xml|php|sh)".toRegex()
        externalStorageManager.getFileForRead(this, template) {
            if (it.length() > 1024 * 100 /*100Kb*/) {
                // Because i can
                toast("file is very large")
                return@getFileForRead
            }
            val textView = findViewById<TextView>(R.id.logView)
            textView.text = it.readText()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK) return
        if (data == null) return
        when (requestCode) {
            ExternalStorageManager.SUGGESTION_EXTERNAL_STORAGE_REQUEST_CODE -> {
                externalStorageManager.onSuggestionExternalStorageActivityResult(data)
            }
            ExternalStorageManager.MANUAL_EXTERNAL_STORAGE_REQUEST_CODE -> {
                externalStorageManager.onManualExternalStorageActivityResult(data)
            }
        }
    }
}
