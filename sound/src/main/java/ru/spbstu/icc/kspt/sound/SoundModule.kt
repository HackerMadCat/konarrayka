package ru.spbstu.icc.kspt.sound

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast

class SoundModule : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sound_module)
    }

    fun toastMe (view: View){
        val testToast = Toast.makeText(this,"TTTTTTT", Toast.LENGTH_SHORT)
        testToast.show()
    }

    fun rec (v:View){
        val recIntent = Intent(this,RecordSound::class.java)
        startActivity(recIntent)
    }



}