package ru.spbstu.icc.kspt.configuration.model

import java.io.File
import java.io.Serializable

data class Hero(
        val icon: File,
        val name: String,
        val color: Int
):Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Hero) return false
        return name == other.name
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}