package ru.spbstu.icc.kspt

import ru.spbstu.icc.kspt.model.Action
import ru.spbstu.icc.kspt.model.Role

class ActionImpl(vararg roles: Role, private val text: String) : Action {
    private val roles = roles.toList()

    override fun getText(): String {
        return text
    }

    override fun getRoles(): List<Role> {
        return roles
    }
}
