package ru.spbstu.icc.kspt.sound

import java.io.File

interface SoundManager {
    fun loadSound(file: File): Sound
}
