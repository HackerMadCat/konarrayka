package com.fortu

import com.fortu.configuration.model.Action
import com.fortu.model.Role

class ActionImpl(vararg roles: Role, private val text: String) : Action {
    private val roles = roles.toList()

    override fun getText(): String {
        return text
    }

    override fun getRoles(): List<Role> {
        return roles
    }
}
