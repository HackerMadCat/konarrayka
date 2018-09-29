package ru.spbstu.icc.kspt.sound

import java.io.File

class SoundManager {
    fun loadSound(file: File): Sound {
        return Sound(file)
    }
}
