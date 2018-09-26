package com.fortu.model

interface Model {
    fun getRoles(): List<Role>

    fun addRole(role: Role)

    fun getActions(): List<Action>

    fun addAction(pos: Int, action: Action)

    fun addAction(action: Action)
}