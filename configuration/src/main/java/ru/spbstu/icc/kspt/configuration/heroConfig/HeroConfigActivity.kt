package ru.spbstu.icc.kspt.configuration.heroConfig

import android.app.Activity
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_hero_config.*
import ru.spbstu.icc.kspt.configuration.R
import ru.spbstu.icc.kspt.configuration.model.Hero
import java.io.File
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.View
import ru.spbstu.icc.kspt.common.CallManager
import ru.spbstu.icc.kspt.storage.*

class HeroConfigActivity : AppCompatActivity() {

    private var iconFile: File? = null
    private val callManager = CallManager<Int, PaletteActivity>(PaletteActivity.RESULT)
    private val externalStorageManager = ExternalStorageManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hero_config)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> {
//                val hero = Hero(iconFile!!,heroNameField.text.toString(),(imageButton.background as ColorDrawable).color)
                intent.putExtra(RESULT,Hero(iconFile!!,heroNameField.text.toString(),(imageButton.background as ColorDrawable).color))
                setResult(Activity.RESULT_OK,intent)
                this.finish()
                return true
            }
            else-> return super.onOptionsItemSelected(item)
        }
    }
    fun chooseColor(v:View){
        callPalette {(imageButton.background as ColorDrawable).color = it}
    }

    fun callPalette (callback: (Int)->Unit ){
        callManager.call(this, REQUEST_CODE_COLOR,callback)
    }

    fun chooseIcon(v:View){
        val template = ".*\\.(png|jpg|jpeg)".toRegex()
        externalStorageManager.getFileForRead(this, template){
            iconFile = File(it.path)
            val myBit: Bitmap = BitmapFactory.decodeFile(iconFile?.absolutePath)
            heroPicture.setImageBitmap(myBit)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode!= Activity.RESULT_OK) return
        if(data == null) return
        when(requestCode){
            REQUEST_CODE_COLOR -> (imageButton.background as ColorDrawable).color = data.getIntExtra("Color",0)
            ExternalStorageManager.SUGGESTION_EXTERNAL_STORAGE_REQUEST_CODE -> externalStorageManager.onActivityResult(requestCode,resultCode,data)
        }
    }

    companion object {
        const val RESULT = "ru.spbstu.icc.kspt.configuration.heroConfig.HeroConfigActivity.RESULT"
        const val REQUEST_CODE_COLOR = 8999
    }
}
