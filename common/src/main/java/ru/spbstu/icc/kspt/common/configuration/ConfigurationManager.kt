package ru.spbstu.icc.kspt.common.configuration

import ru.spbstu.icc.kspt.common.model.Model
import ru.spbstu.icc.kspt.common.model.Scenario
import java.io.File

interface ConfigurationManager {
    fun loadModel(file: File): Model

    fun getScenario(model: Model): Scenario
}
