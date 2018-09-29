package ru.spbstu.icc.kspt.common.model

interface Action {
    fun getText() : String

    fun getRoles(): List<Role>
}
