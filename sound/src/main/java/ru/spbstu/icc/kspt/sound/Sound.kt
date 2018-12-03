package ru.spbstu.icc.kspt.sound

import java.io.File
import java.io.Serializable

data class Sound(val file: File, val volume: Int): Serializable
