package com.fortu.openapi.configuration

import com.fortu.model.Model
import com.fortu.model.Scenario
import java.io.File

interface ConfigurationManager {
    fun loadModel(file: File): Model

    fun getScenario(model: Model): Scenario
}
