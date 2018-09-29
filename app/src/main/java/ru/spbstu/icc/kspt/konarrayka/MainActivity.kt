package ru.spbstu.icc.kspt.konarrayka

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ru.spbstu.icc.kspt.configuration.ConfigurationManagerImpl
import ru.spbstu.icc.kspt.storage.StorageManagerImpl

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val configurationManager = ConfigurationManagerImpl()
        val storageManager = StorageManagerImpl()
        val file = storageManager.findFile()
        val model = configurationManager.loadModel(file)
        val scenario = configurationManager.getScenario(model)
        for (action in scenario.getActions()) {
            println("-----------------")
            println(action.getRoles().joinToString(" and ") { it.getName() })
            println(action.getText())
        }
    }
}
