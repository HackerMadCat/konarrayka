package ru.spbstu.icc.kspt

import ru.spbstu.icc.kspt.model.Role

data class RoleImpl(private val name: String): Role {
    override fun getName(): String {
        return name
    }
}
