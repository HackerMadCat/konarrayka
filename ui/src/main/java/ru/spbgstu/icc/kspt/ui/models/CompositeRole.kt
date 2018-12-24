package ru.spbgstu.icc.kspt.ui.models

sealed class CompositeRole {
    object Condition : CompositeRole()

    class Role(val name: String, val color: Int) : CompositeRole()
}