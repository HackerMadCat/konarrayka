package ru.spbstu.icc.kspt.konarrayka

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import ru.spbstu.icc.kspt.configuration.ConfigurationManager
import ru.spbstu.icc.kspt.storage.ExternalStorageManager
import java.io.File
import java.lang.StringBuilder
import java.util.logging.Logger

class MainActivity : AppCompatActivity() {

    private val externalStorageManager = ExternalStorageManager(this)

    private val logger = Logger.getLogger(javaClass.name)

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
        externalStorageManager.getFileForRead("file-.*\\.txt".toRegex()) {
            val textView = findViewById<TextView>(R.id.logView)
            textView.text = it.readText()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK) {
            logger.severe("Wrong activity result")
            return
        }
        if (data == null) {
            logger.severe("Unregistered intent")
            return
        }
        when (requestCode) {
            ExternalStorageManager.REQUEST_CODE -> externalStorageManager.onActivityResult(data)
        }
    }
}
