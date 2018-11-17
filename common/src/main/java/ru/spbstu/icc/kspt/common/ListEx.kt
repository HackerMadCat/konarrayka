package ru.spbstu.icc.kspt.common

import java.util.*


/**
 * Returns a random element.
 */
fun <E> List<E>.random(): E? = random(Random())

/**
 * Returns a random element using the specified [random] instance as the source of randomness.
 */
fun <E> List<E>.random(random: Random): E? = if (isNotEmpty()) get(random.nextInt(size)) else null
