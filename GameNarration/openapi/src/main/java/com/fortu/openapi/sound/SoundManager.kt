package com.fortu.sound

import java.io.File

interface SoundManager {
    fun loadSound(file: File): Sound
}
