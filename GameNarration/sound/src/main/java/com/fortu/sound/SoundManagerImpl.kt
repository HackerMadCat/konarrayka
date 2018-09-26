package com.fortu.sound

import com.fortu.sound.sound.Sound
import com.fortu.sound.sound.SoundManager
import java.io.File

class SoundManagerImpl : SoundManager {
    override fun loadSound(file: File): Sound {
        return SoundImpl(file)
    }
}
