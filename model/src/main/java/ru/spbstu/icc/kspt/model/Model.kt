package ru.spbstu.icc.kspt.model

class Model {

    private val roles = ArrayList<Role>()
    private val actions = ArrayList<Action>()

    fun getRoles(): List<Role> {
        return roles
    }

    fun addRole(role: Role) {
        roles.add(role)
    }

    fun getActions(): List<Action> {
        return actions
    }

    fun addAction(pos: Int, action: Action) {
        actions.add(pos, action)
    }

    fun addAction(action: Action) {
        actions.add(action)
    }
}