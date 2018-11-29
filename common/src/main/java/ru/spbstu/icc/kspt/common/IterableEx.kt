package ru.spbstu.icc.kspt.common

import java.util.*


fun <E> Iterable<E>.random(): E? = random(Random())

fun <E> Iterable<E>.random(random: Random): E? = this.toList().random(random)

fun <E> List<E>.random(random: Random): E? = if (isNotEmpty()) get(random.nextInt(size)) else null

fun <E> Iterable<E>.randomDrop(): Iterable<E> = randomDrop(Random())

fun <E> Iterable<E>.randomDrop(random: Random): Iterable<E> = this.toList().randomDrop(random)

fun <E> List<E>.randomDrop(random: Random): Iterable<E> = shuffled(random).drop(random.nextInt(size))

fun <E> Iterable<E>.add(element: E): Iterable<E> = toList().add(element)

fun <E> List<E>.add(element: E): List<E> = this + listOf(element)
