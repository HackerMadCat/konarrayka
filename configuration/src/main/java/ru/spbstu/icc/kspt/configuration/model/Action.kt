package ru.spbstu.icc.kspt.configuration.model

import java.io.File

data class Action(
        val icon: File,
        val name: String,
        val description: String,
        val voices: List<File>,
        val duration: Duration
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Action) return false
        return name == other.name
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}