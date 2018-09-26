package ru.spbstu.icc.kspt.model

interface Action {
    fun getText() : String

    fun getRoles(): List<Role>
}
