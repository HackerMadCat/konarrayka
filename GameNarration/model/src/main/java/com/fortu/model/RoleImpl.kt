package com.fortu

import com.fortu.model.Role

data class RoleImpl(private val name: String): Role {
    override fun getName(): String {
        return name
    }
}
