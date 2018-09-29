package ru.spbstu.icc.kspt.model


class Action(vararg roles: Role, private val text: String) {
    private val roles = roles.toList()

    fun getText(): String {
        return text
    }

    fun getRoles(): List<Role> {
        return roles
    }
}
