package ru.spbstu.icc.kspt.model

class Scenario(private val actions: List<Action>) {

    fun getActions(): List<Action> {
        return actions
    }
}
