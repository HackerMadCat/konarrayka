package ru.spbstu.icc.kspt.common.sound

import java.io.File

interface SoundManager {
    fun loadSound(file: File): Sound
}
