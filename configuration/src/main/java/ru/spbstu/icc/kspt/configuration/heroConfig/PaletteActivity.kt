package ru.spbstu.icc.kspt.configuration.heroConfig

import android.app.Activity
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import ru.spbstu.icc.kspt.configuration.R

class PaletteActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_palette)
    }

    fun setColor (v:View){
        intent.putExtra(RESULT,(v.background as ColorDrawable).color)
        setResult(Activity.RESULT_OK,intent)
        this.finish()
    }

    companion object {
        const val RESULT = "ru.spbstu.icc.kspt.configuration.heroConfig.PaletteActivity.RESULT"
    }
}