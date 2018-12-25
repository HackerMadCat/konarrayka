package ru.spbstu.icc.kspt.configuration.manager

import android.app.Activity
import android.content.Intent
import ru.spbstu.icc.kspt.common.CallManager
import ru.spbstu.icc.kspt.configuration.actionConfig.ActionConfigActivity
import ru.spbstu.icc.kspt.configuration.heroConfig.HeroConfigActivity
import ru.spbstu.icc.kspt.configuration.model.Action
import ru.spbstu.icc.kspt.configuration.model.Hero

class ActionManager(private val activity: Activity) {

    private val callManager = CallManager<Action, ActionConfigActivity>(ActionConfigActivity.RESULT)

    fun configurateAction(callback: (Action)->Unit){
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