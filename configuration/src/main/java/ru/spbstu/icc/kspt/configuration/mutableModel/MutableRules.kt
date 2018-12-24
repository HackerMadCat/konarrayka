package ru.spbstu.icc.kspt.configuration.mutableModel

import ru.spbstu.icc.kspt.configuration.ConditionElement
import ru.spbstu.icc.kspt.configuration.ConditionElement.HeroCE
import ru.spbstu.icc.kspt.configuration.ConditionElement.OrCE
import ru.spbstu.icc.kspt.configuration.model.Action
import ru.spbstu.icc.kspt.configuration.model.Hero
import ru.spbstu.icc.kspt.configuration.model.Rules

data class MutableRules(
        val actions: MutableList<Action>,
        val heroes: MutableList<Hero>,
        val conditionalActions: MutableList<MutableConditionalAction>
) {
    val conditionElements: List<ConditionElement>
        get() = listOf(OrCE) + heroes.map { HeroCE(it) }
}

fun Rules.asMutable() = MutableRules(
        actions = actions.toMutableList(),
        heroes = heroes.toMutableList(),
        conditionalActions = conditionalActions.map { it.asMutable() }.toMutableList()
)