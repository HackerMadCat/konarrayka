package ru.spbstu.icc.kspt.configuration

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

fun ViewGroup.inflate(@LayoutRes resource: Int, attachToRoot: Boolean = false): View =
        LayoutInflater.from(context).inflate(resource, this, attachToRoot)

fun ViewGroup.children(): List<View> =
        (0 until childCount).map { getChildAt(it) }
