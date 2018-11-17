package ru.spbstu.icc.kspt.configuration.model

import java.io.File

data class ModelHeader(val id: Int, val icon: File, val name: String) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ModelHeader) return false
        return id == other.id
    }

    override fun hashCode(): Int {
        return id
    }
}
