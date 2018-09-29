package ru.spbstu.icc.kspt.sound

import ru.spbstu.icc.kspt.common.sound.Sound
import java.io.File

data class SoundImpl(val file: File) : Sound
