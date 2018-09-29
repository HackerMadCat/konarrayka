package ru.spbstu.icc.kspt.model

import ru.spbstu.icc.kspt.common.model.Action
import ru.spbstu.icc.kspt.common.model.Scenario

class ScenarioImpl(private val actions: List<Action>) : Scenario {

    override fun getActions(): List<Action> {
        return actions
    }
}
