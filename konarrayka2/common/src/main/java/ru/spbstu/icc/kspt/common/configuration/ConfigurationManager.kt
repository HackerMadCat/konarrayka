package ru.spbstu.icc.kspt.configuration

import ru.spbstu.icc.kspt.model.Model
import ru.spbstu.icc.kspt.model.Scenario
import java.io.File

interface ConfigurationManager {
    fun loadModel(file: File): Model

    fun getScenario(model: Model): Scenario
}
