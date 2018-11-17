package ru.spbstu.icc.kspt.configuration.model

data class Condition(val disjunctions: List<Set<Hero>>) {
    fun evaluate(heroes: Set<Hero>): Boolean {
        for (conjunction in disjunctions) {
            if (heroes.containsAll(conjunction)) {
                return true
            }
        }
        return false
    }
}