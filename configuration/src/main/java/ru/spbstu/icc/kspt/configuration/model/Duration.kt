package ru.spbstu.icc.kspt.configuration.model

sealed class Duration {

    object Sound : Duration()

    data class Time(val value: Int) : Duration()

    data class Variable(val name: String, val default: Time) : Duration() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Variable) return false
            return name == other.name
        }

        override fun hashCode(): Int {
            return name.hashCode()
        }

        override fun toString(): String {
            return name
        }
    }
}