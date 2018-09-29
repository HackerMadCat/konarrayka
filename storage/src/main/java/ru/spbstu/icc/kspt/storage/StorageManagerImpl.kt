package ru.spbstu.icc.kspt.storage

import ru.spbstu.icc.kspt.common.storage.StorageManager
import java.io.File

class StorageManagerImpl : StorageManager {
    override fun findFile(): File {
        return File("path/to/file")
    }
}
