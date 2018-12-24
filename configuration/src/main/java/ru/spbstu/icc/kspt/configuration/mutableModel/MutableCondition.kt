package ru.spbstu.icc.kspt.configuration.mutableModel

import ru.spbstu.icc.kspt.configuration.ConditionElement
import ru.spbstu.icc.kspt.configuration.ConditionElement.HeroCE
import ru.spbstu.icc.kspt.configuration.ConditionElement.OrCE
import ru.spbstu.icc.kspt.configuration.model.Condition
import ru.spbstu.icc.kspt.configuration.model.Hero

data class MutableCondition(
        val disjunctions: MutableList<MutableSet<Hero>>
) {

    fun addOr(): Boolean {
        val last = disjunctions.lastOrNull() ?: return false
        if (last.isNotEmpty()) return false
        disjunctions.add(LinkedHashSet())
        return true
    }

    fun addHero(hero: Hero): Boolean {
        return disjunctions.lastOrAdd { LinkedHashSet() }.add(hero)
    }

    fun flatten(): List<ConditionElement> =
            disjunctions.map { it.map { h -> HeroCE(h) } }
                    .map { it + listOf(OrCE) }
                    .flatten().dropLast(1)

    companion object {
        private fun <E> MutableList<E>.lastOrAdd(default: () -> E): E {
            val last = lastOrNull()
            if (last != null) return last
            val element = default()
            add(element)
            return element
        }
    }
}

fun Condition.asMutable() = MutableCondition(
        disjunctions.map {
            LinkedHashSet(it)
        }.toMutableList()
)
