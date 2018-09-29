package ru.spbstu.icc.kspt.konarrayka

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import ru.spbstu.icc.kspt.configuration.ConfigurationManagerImpl
import ru.spbstu.icc.kspt.storage.StorageManagerImpl

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //val intent = Intent()


        val tv_dynamic = TextView(this)
        tv_dynamic.textSize = 20f
        tv_dynamic.text = "This is a dynamic TextView generated programmatically in Kotlin"

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


/*
println("hello world")
val configurationManager = ConfigurationManagerImpl()
val storageManager = StorageManagerImpl()
val file = storageManager.findFile()
val model = configurationManager.loadModel(file)
val scenario = configurationManager.getScenario(model)
for (action in scenario.getActions()) {
    println("-----------------")
    println(action.getRoles().joinToString(" and ") { it.getName() })
    println(action.getText())
}*/

