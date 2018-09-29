package ru.spbstu.icc.kspt.model

import ru.spbstu.icc.kspt.common.model.Role

data class RoleImpl(private val name: String): Role {
    override fun getName(): String {
        return name
    }
}
