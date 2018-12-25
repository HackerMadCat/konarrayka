package ru.spbstu.icc.kspt.konarrayka

import android.app.Notification
import android.app.NotificationChannel
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.SeekBar
import kotlinx.android.synthetic.main.mainsettings_activity.*
import android.app.NotificationManager
import android.os.Build



class MainSettings: AppCompatActivity(){

    private var volumeSeekbar: SeekBar? = null
    private var audioManager: AudioManager? = null
    private var notification: NotificationManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        volumeControlStream = AudioManager.STREAM_MUSIC
        setContentView(R.layout.mainsettings_activity)
        initControls()
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.agree_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if(item.itemId == R.id.action_agree) {
            startActivity(Intent(this, StartActivity::class.java))
            true
        }
        else super.onOptionsItemSelected(item)
    }


    private fun initControls() {
        try
        {
            volumeSeekbar = findViewById(R.id.seekBar1)
            audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
            volumeSeekbar!!.max = audioManager!!.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
            volumeSeekbar!!.progress = audioManager!!.getStreamVolume(AudioManager.STREAM_MUSIC)
            volumeSeekbar!!.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
                override fun onStopTrackingTouch(arg0:SeekBar) {}
                override fun onStartTrackingTouch(arg0:SeekBar) {}
                override fun onProgressChanged(arg0:SeekBar, progress:Int, arg2:Boolean) {
                    audioManager!!.setStreamVolume(AudioManager.STREAM_MUSIC,
                            progress, 0)
                }
            })
            val notificationChannels : NotificationChannel? = null
            if(checkBox.isActivated){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    if (notificationChannels != null) {
                        notificationChannels.enableVibration(true)
                    }
                }
            }
            else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (notificationChannels != null) {
                    notificationChannels.enableVibration(false)
                }
            }
        }
        catch (e:Exception) {
            e.printStackTrace()
        }
    }
}
