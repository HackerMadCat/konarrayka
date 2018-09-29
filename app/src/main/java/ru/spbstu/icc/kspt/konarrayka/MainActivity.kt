package ru.spbstu.icc.kspt.konarrayka

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import ru.spbstu.icc.kspt.configuration.ConfigurationManagerImpl
import ru.spbstu.icc.kspt.storage.StorageManagerImpl
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val logView = findViewById<TextView>(R.id.logView)
        logView.text = generateText()
    }

    private fun generateText() = StringBuilder().run {
        appendln("hello world!")
        val configurationManager = ConfigurationManagerImpl()
        val storageManager = StorageManagerImpl()
        val file = storageManager.findFile()
        val model = configurationManager.loadModel(file)
        val scenario = configurationManager.getScenario(model)
        for (action in scenario.getActions()) {
            appendln("-----------------")
            appendln(action.getRoles().joinToString(" and ") { it.getName() })
            appendln(action.getText())
        }
        toString()
    }
}
