package ru.spbstu.icc.kspt.storage

import java.io.File

interface StorageManager {
    fun findFile(): File
}
