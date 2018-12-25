package ru.spbstu.icc.kspt.configuration.manager

import android.app.Activity
import android.content.Intent
import ru.spbstu.icc.kspt.common.CallManager
import ru.spbstu.icc.kspt.configuration.heroConfig.HeroConfigActivity
import ru.spbstu.icc.kspt.configuration.model.Hero

class HeroManager(private val activity: Activity) {

    private val callManager = CallManager<Hero, HeroConfigActivity>(HeroConfigActivity.RESULT)

    fun configurateHero(callback: (Hero)->Unit){
        callManager.call(activity, REQUEST_CODE,callback)
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK) return
        if (data == null) return
        when (requestCode) {
            REQUEST_CODE -> callManager.onActivityResult(data)
        }
    }

    companion object {
        const val REQUEST_CODE = 8933
    }
}