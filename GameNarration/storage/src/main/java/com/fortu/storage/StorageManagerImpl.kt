package com.fortu

//import android.os.storage.StorageManager
import com.fortu.openapi.storage.StorageManager
import java.io.File

class StorageManagerImpl : StorageManager {
    override fun findFile(): File {
        return File("path/to/file")
    }
}
