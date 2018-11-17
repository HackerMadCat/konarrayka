package ru.spbstu.icc.kspt.configuration.model

data class Setup(
        val numPlayers: Int,
        val heroes: Set<Hero>
) : Comparable<Setup> {
    override fun compareTo(other: Setup): Int {
        return numPlayers.compareTo(other.numPlayers)
    }
}