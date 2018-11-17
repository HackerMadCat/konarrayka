package ru.spbstu.icc.kspt.configuration.model

import ru.spbstu.icc.kspt.sound.Sound
import java.io.File

data class Action(
        val icon: File,
        val name: String,
        val description: String,
        val voices: List<Sound>,
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

    override fun toString(): String {
        return name
    }
}