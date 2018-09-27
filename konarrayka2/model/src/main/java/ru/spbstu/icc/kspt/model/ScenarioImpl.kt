package ru.spbstu.icc.kspt

import ru.spbstu.icc.kspt.model.Action
import ru.spbstu.icc.kspt.model.Scenario

class ScenarioImpl(private val actions: List<Action>) : Scenario {

    override fun getActions(): List<Action> {
        return actions
    }
}
