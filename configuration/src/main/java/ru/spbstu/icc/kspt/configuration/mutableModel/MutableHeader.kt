package ru.spbstu.icc.kspt.configuration.mutableModel

import ru.spbstu.icc.kspt.configuration.model.Header
import java.io.File

data class MutableHeader(
        val id: Int,
        var icon: File,
        var name: String
)

fun Header.asMutable() = MutableHeader(id, icon, name)
