package ru.spbstu.icc.kspt.configuration.model

import java.io.File

data class Model(
        val header: ModelHeader,
        val rules: Rules,
        val sounds: List<File>,
        val setups: List<Setup>
) {
    val timeVariables: Set<Duration.Variable> by lazy {
        rules.actions.map { it.duration }.filterIsInstance<Duration.Variable>().toSet()
    }

    fun getScenario(setup: Setup): Scenario {
        val heroes = setup.heroes
        val actions = ArrayList<Action>()
        for ((condition, action) in rules.conditionalActions) {
            if (condition.evaluate(heroes)) {
                actions.add(action)
            }
        }
        return Scenario(actions)
    }
}
