package ru.spbstu.icc.kspt.configuration.model

data class Rules(
        val heroes: Set<Hero>,
        val actions: Set<Action>,
        val conditionalActions: List<ConditionalAction>
)