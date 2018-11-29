package ru.spbstu.icc.kspt.configuration.settings

import ru.spbstu.icc.kspt.common.random
import ru.spbstu.icc.kspt.configuration.model.Duration
import ru.spbstu.icc.kspt.configuration.model.Model
import java.io.File

data class GameSettings(
        val sound: File?,
        val timeVariables: Map<Duration.Variable, Duration.Time>
) {
    constructor(model: Model) : this(
            model.sounds.random(),
            model.timeVariables.map { it to it.default }.toMap()
    )
}