package ru.spbstu.icc.kspt.common

import android.app.Activity
import android.content.Intent
import java.lang.IllegalArgumentException
import java.util.concurrent.atomic.AtomicInteger
import kotlin.reflect.full.companionObject
import kotlin.reflect.full.memberProperties

/**
 * Type parameter T must be not nullable
 */
class CallManager<T>(private val name: String, private val activityToCall: Class<out Activity>) {

    private val callbacks = HashMap<Int, (T) -> Unit>()

    private val idGenerator = AtomicInteger()

    fun call(activity: Activity, requestCode: Int, callback: (T) -> Unit, configure: Intent.() -> Unit) {
        val id = idGenerator.incrementAndGet()
        callbacks[id] = callback
        val intent = Intent(activity, activityToCall)
        intent.putExtra(CALLBACK_ID, id)
        intent.configure()
        activity.setResult(Activity.RESULT_OK, intent)
        activity.startActivityForResult(intent, requestCode)
    }

    fun onActivityResult(data: Intent) {
        val extras = data.extras ?: return
        val result = extras.get(name) ?: return
        val id = data.getExtra<Int>(CALLBACK_ID)
        @Suppress("UNCHECKED_CAST")
        callbacks.remove(id)?.invoke(result as T)
    }

    companion object {
        const val CALLBACK_ID = "ru.spbstu.icc.kspt.storage.ExternalStorageManager.CALLBACK_ID"
        const val PROPERTY_NAME_OF_EXTRA_RESULT = "RESULT"

        inline operator fun <T, reified A : Activity> invoke(): CallManager<T> {
            val companion = A::class.companionObject
                    ?: throw IllegalArgumentException("Class ${A::class}.Companion not found")
            val companionProp = companion.memberProperties.find { it.name == PROPERTY_NAME_OF_EXTRA_RESULT }
                    ?: throw IllegalArgumentException("Property $companion.$PROPERTY_NAME_OF_EXTRA_RESULT not found")
            val name = companionProp.getter.call(companionProp) as? String
                    ?: throw IllegalArgumentException("Property $companion.$PROPERTY_NAME_OF_EXTRA_RESULT must have a type String")
            return CallManager(name, A::class.java)
        }

        inline operator fun <T, reified A : Activity> invoke(name: String) =
                CallManager<T>(name, A::class.java)
    }
}