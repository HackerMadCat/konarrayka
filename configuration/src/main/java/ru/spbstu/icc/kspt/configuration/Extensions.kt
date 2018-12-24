package ru.spbstu.icc.kspt.configuration

import androidx.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

fun ViewGroup.inflate(@LayoutRes resource: Int, attachToRoot: Boolean = false): View =
        LayoutInflater.from(context).inflate(resource, this, attachToRoot)
                ?: TODO("Yaroslav check this case")
