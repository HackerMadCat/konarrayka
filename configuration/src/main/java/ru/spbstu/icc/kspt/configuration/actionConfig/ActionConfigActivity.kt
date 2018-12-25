package ru.spbstu.icc.kspt.configuration.actionConfig

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_action_config.*
import ru.spbstu.icc.kspt.common.CallManager
import ru.spbstu.icc.kspt.common.add
import ru.spbstu.icc.kspt.configuration.R
import ru.spbstu.icc.kspt.configuration.model.Action
import ru.spbstu.icc.kspt.sound.RecordSound
import ru.spbstu.icc.kspt.sound.Sound
import ru.spbstu.icc.kspt.sound.SoundManager
import ru.spbstu.icc.kspt.storage.ExternalStorageManager
import java.io.File


class ActionConfigActivity : AppCompatActivity() {
    var icon:File? = null
    var name:String? = null
    var description:String? = null
    val voices: List<File>? = null
    var duration: Int? = null

    private val songs:ArrayList<String>? = ArrayList()
    var adapter:ArrayAdapter<String>? = null
    var voicesList:ListView? = null
    private val externalStorageManager = ExternalStorageManager()

    private val callManager = CallManager<Sound,RecordSound>(RecordSound.RESULT)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_action_config)

        voices?.forEach { songs?.add(it.name) }

        voicesList = findViewById(R.id.SoundList)
        adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,songs)
        voicesList?.adapter = adapter

        fab.setOnClickListener {
            callManager.call(this, SoundManager.REQUEST_CODE){
                voices?.add(File(it.file.path))
                songs?.add(it.file.name)
                adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,songs)
                voicesList?.adapter = adapter
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> {
                name = actionName.text.toString()
                description = actionDesc.text.toString()
                duration = actionDur.text.toString().toInt()
                val hero = Action(icon!!,name!!,description!!,voices!!,duration!!)
                intent.putExtra("Hero",hero)
                setResult(Activity.RESULT_OK,intent)
                this.finish()
                return true
            }
            else-> return super.onOptionsItemSelected(item)
        }
    }

    fun chooseIcon(v:View){
        val template = ".*\\.(png|jpg|jpeg)".toRegex()
        externalStorageManager.getFileForRead(this, template){
            icon = File(it.path)
            val myBit: Bitmap = BitmapFactory.decodeFile(icon?.absolutePath)
            actionPict.setImageBitmap(myBit)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode!= Activity.RESULT_OK) return
        if(data == null) return
        when(requestCode){
            REQUEST_CODE -> callManager.onActivityResult(data)
        }
    }
    companion object {
        const val REQUEST_CODE = 8912
        const val RESULT = "ru.spbstu.icc.kspt.configuration.actionConfig.ActionConfigActivity.RESULT"
    }
}
