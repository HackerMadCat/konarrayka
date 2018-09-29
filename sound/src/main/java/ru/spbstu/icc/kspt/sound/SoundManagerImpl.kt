package ru.spbstu.icc.kspt.sound

import ru.spbstu.icc.kspt.common.sound.Sound
import ru.spbstu.icc.kspt.common.sound.SoundManager
import java.io.File

class SoundManagerImpl : SoundManager {
    override fun loadSound(file: File): Sound {
        return SoundImpl(file)
    }
}
