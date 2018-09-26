package com.fortu

import com.fortu.model.Action
import com.fortu.model.Role
import com.fortu.model.Model

class ModelImpl : Model {

    private val roles = ArrayList<Role>()
    private val actions = ArrayList<Action>()

    override fun getRoles(): List<Role> {
        return roles
    }

    override fun addRole(role: Role) {
        roles.add(role)
    }

    override fun getActions(): List<Action> {
        return actions
    }

    override fun addAction(pos: Int, action: Action) {
        actions.add(pos, action)
    }

    override fun addAction(action: Action) {
        actions.add(action)
    }
}