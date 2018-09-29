package ru.spbstu.icc.kspt.model

import ru.spbstu.icc.kspt.common.model.Action
import ru.spbstu.icc.kspt.common.model.Role

class ActionImpl(vararg roles: Role, private val text: String) : Action {
    private val roles = roles.toList()

    override fun getText(): String {
        return text
    }

    override fun getRoles(): List<Role> {
        return roles
    }
}
