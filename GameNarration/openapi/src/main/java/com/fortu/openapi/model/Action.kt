package com.fortu.model

interface Action {
    fun getText() : String

    fun getRoles(): List<Role>
}
