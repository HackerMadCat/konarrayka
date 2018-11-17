package ru.spbstu.icc.kspt.configuration

import ru.spbstu.icc.kspt.configuration.model.Hero

sealed class ConditionElement {
    data class HeroCE(val hero: Hero) : ConditionElement()
    object OrCE : ConditionElement()
}