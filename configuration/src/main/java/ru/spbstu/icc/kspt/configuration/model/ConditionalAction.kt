package ru.spbstu.icc.kspt.configuration.model

data class ConditionalAction(
        val condition: Condition,
        val action: Action
)