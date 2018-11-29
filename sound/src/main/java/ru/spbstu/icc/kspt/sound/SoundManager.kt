package ru.spbstu.icc.kspt.sound

import android.app.Activity
import android.content.Intent
import ru.spbstu.icc.kspt.common.CallManager

class SoundManager(private val activity: Activity) {

    private val callManager = CallManager<Sound, RecordSound>(RecordSound.RESULT)

    fun play(sound: Sound) {
        TODO()
    }

    fun stop(sound: Sound) {
        TODO()
    }

    fun record(name: String, callback: (Sound) -> Unit) {
        callManager.call(activity, REQUEST_CODE, callback) {
            putExtra(RecordSound.SOUND_NAME, name)
        }
    }

    fun getDuration(sound: Sound): Int {
        TODO()
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK) return
        if (data == null) return
        when (requestCode) {
            REQUEST_CODE -> callManager.onActivityResult(data)
        }
    }

    companion object {
        const val REQUEST_CODE = 8931
    }
}