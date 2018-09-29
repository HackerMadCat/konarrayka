package ru.spbstu.icc.kspt.common.storage

import java.io.File

interface StorageManager {
    fun findFile(): File
}
