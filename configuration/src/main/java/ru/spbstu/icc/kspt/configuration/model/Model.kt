package ru.spbstu.icc.kspt.configuration.model

import ru.spbstu.icc.kspt.sound.Sound
import java.io.File

data class Model(
        val id: Int,
        val icon: File,
        val name: String,
        val rules: Rules,
        val sounds: List<Sound>,
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
