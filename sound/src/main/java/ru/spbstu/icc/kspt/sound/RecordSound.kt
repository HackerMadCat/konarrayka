package ru.spbstu.icc.kspt.sound

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.ActionBar
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_record_sound.*

import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.timerTask

class RecordSound : AppCompatActivity() {
    private lateinit var mediaRecorder: MediaRecorder
    private lateinit var mediaPlayer: MediaPlayer
    private var permissionToRecord = false
    private var permissions: Array<String> = arrayOf(Manifest.permission.RECORD_AUDIO)
    private var file:File? = null
    private lateinit var timer:Timer

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
        val actionBar:ActionBar? = supportActionBar
        actionBar?.setHomeButtonEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        stop_button.isEnabled = false
        ActivityCompat.requestPermissions(this,permissions, REQUEST_RECORD_AUDIO_PERMISSION)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> {
                if (file == null){
                    this.finish()
                    return true
                }
                intent.putExtra(RESULT,file?.path)
                setResult(Activity.RESULT_OK,intent)
                this.finish()
                return true
            }
            else-> return super.onOptionsItemSelected(item)
        }
    }

    //Create Filename by date
    private fun createFileName():String{
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        DurationText.text =  "Sound${timestamp}"
        return "Sound${timestamp}"
    }

    //release the media recorder, set record param and start record
    private fun recordStart(v:View) {
        if (editText.text.isEmpty()) {
            DurationText.text = "FieldIsEmpty"
            Log.e("EditableTest","EditableIsEmpty================================")
            return
        }
        if (file == null) {file = File(this.filesDir,editText.text.toString()+".3gp")}
        mediaRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB)
            setOutputFile(file?.path)
//            setAudioSamplingRate(44100)
//            setAudioEncodingBitRate()
            DurationText.text = file?.nameWithoutExtension
            try {
                prepare()
            } catch (e: IOException){
                Log.e("RecordTest","<====prepare() failed=====>")
            }
            start()
        }
        rec_button.isEnabled = false
        stop_button.isEnabled = true
        changeButtonTaskAndDrawable(stop_button,"stop","recStop")
    }

    //release recorder and stop recording
    private fun recordStop (v:View){
        mediaRecorder.apply {
            stop()
            release()
        }
        rec_button.isEnabled = true
        changeButtonTaskAndDrawable(stop_button,"play","play")
    }

    //set path to the file to listening and start playing
    private fun playStart (v:View){
        mediaPlayer = MediaPlayer().apply {
            try {
                setDataSource(file?.path)
                prepare()
                start()

            } catch (e: IOException){
                Log.e("RecordTest","<====prepare() failed ====>")
            }
        }
        timer = Timer()
        timer.schedule( timerTask { changeButtonTaskAndDrawable(stop_button,"play","play") },
                mediaPlayer.duration.toLong())
        changeButtonTaskAndDrawable(stop_button,"stop","stop")
    }

    //stop playing file
    private fun playStop (v:View){
        mediaPlayer.release()
        timer.cancel()
        changeButtonTaskAndDrawable(stop_button,"play","play")
    }

    private fun changeButtonTaskAndDrawable(button:ImageView,drawable:String,task:String) {
        button.setImageResource(resources.getIdentifier(drawable, "drawable", packageName))
        when (task) {
            "play" -> button.setOnClickListener { playStart(button) }
            "stop" -> button.setOnClickListener { playStop(button) }
            "recStop" -> button.setOnClickListener { recordStop(button) }
            else -> return
        }
    }
    companion object {
        //permission code
        private const val REQUEST_RECORD_AUDIO_PERMISSION = 200
        const val RESULT = "ru.spbstu.icc.kspt.sound.RecordSound.RESULT"
        const val SOUND_NAME = "ru.spbstu.icc.kspt.sound.RecordSound.SOUND_NAME"
    }
}