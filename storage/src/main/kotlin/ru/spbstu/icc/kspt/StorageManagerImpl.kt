package ru.spbstu.icc.kspt

import ru.spbstu.icc.kspt.storage.StorageManager
import java.io.File

class StorageManagerImpl : StorageManager {
    override fun findFile(): File {
        return File("path/to/file")
    }
}
