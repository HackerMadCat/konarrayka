package ru.spbstu.icc.kspt.model


data class Role(private val name: String) {
    fun getName(): String {
        return name
    }
}
