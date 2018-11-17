package ru.spbstu.icc.kspt.sound

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_record_sound.*

import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class RecordSound : AppCompatActivity() {
    private var filename: String? = null
    private var mediaRecorder: MediaRecorder? = null
    private var mediaPlayer: MediaPlayer? = null
    private var permissionToRecord = false
    private var permissions: Array<String> = arrayOf(Manifest.permission.RECORD_AUDIO)
    private lateinit var file:File

    //request for RecordPermission
    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionToRecord = if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        } else {
            false
        }
        if (!permissionToRecord) finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record_sound)
//        filename = "${externalCacheDir.absolutePath}/${FileName.text}.3gp"
//        file = File(this.filesDir,FileName.text.toString())
//        filename = file?.path
        ActivityCompat.requestPermissions(this,permissions, REQUEST_RECORD_AUDIO_PERMISSION)
        textView3.text = filename
    }

    //Create Filename by date
    private fun createFileName():String{
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        return "REC_${timestamp}"
    }

    //release the media recorder, set record param and start record
    fun recordStart(v:View) {
//        filename = "${externalCacheDir.absolutePath}/${FileName.text}.3gp"
        file = File(filesDir,FileNameField.text.toString())
        val outFile = File(this.filesDir, FileNameField.text.toString())
        if (outFile.exists()) {
            outFile.delete()
        }
        mediaRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            setOutputFile(outFile.path)
            try {
                prepare()
            } catch (e: IOException){
                Log.e("RecordTest","<====prepare() failed=====>")
            }
            start()
            button3.isEnabled = false
        }
    }

    //release recorder and stop recording
    fun recordStop (v:View){
        mediaRecorder?.apply {
            stop()
            release()
        }
        button3.isEnabled = true
    }

    //set path to the file to listening and start playing
    fun playStart (v:View){
        mediaPlayer = MediaPlayer().apply {
            try {
                setDataSource(file.path)
                prepare()
                start()

            } catch (e: IOException){
                Log.e("RecordTest","<====prepare() failed ====>")
            }
        }
        textView3.text = file.path
    }

    //stop playing file
    fun playStop (v:View){
        mediaPlayer?.release()
        mediaPlayer = null
    }


    companion object {
        //permission code
        private const val REQUEST_RECORD_AUDIO_PERMISSION = 200
        const val RESULT = "ru.spbstu.icc.kspt.sound.RecordSound.RESULT"
        const val SOUND_NAME = "ru.spbstu.icc.kspt.sound.RecordSound.SOUND_NAME"
    }
}