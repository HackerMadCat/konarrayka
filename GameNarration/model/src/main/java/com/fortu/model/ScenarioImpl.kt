package com.fortu

import com.fortu.model.Action
import com.fortu.model.Scenario

class ScenarioImpl(private val actions: List<Action>) : Scenario {

    override fun getActions(): List<Action> {
        return actions
    }
}
