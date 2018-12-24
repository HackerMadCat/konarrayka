package ru.spbstu.icc.kspt.configuration.mutableModel

import ru.spbstu.icc.kspt.configuration.model.Model
import ru.spbstu.icc.kspt.configuration.model.Setup
import java.io.File

data class MutableModel(
        val header: MutableHeader,
        val rules: MutableRules,
        val sounds: MutableList<File>,
        val setups: MutableList<Setup>
)

fun Model.asMutable() = MutableModel(
        header = header.asMutable(),
        rules = rules.asMutable(),
        sounds = sounds.toMutableList(),
        setups = setups.toMutableList()
)
