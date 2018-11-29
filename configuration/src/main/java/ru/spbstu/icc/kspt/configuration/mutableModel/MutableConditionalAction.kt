package ru.spbstu.icc.kspt.configuration.mutableModel

import ru.spbstu.icc.kspt.configuration.model.Action
import ru.spbstu.icc.kspt.configuration.model.ConditionalAction

data class MutableConditionalAction(
        val condition: MutableCondition,
        val action: Action
)

fun ConditionalAction.asMutable() = MutableConditionalAction(
        condition = condition.asMutable(),
        action = action
)
