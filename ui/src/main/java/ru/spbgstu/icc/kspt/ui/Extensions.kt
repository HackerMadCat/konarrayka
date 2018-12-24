package ru.spbgstu.icc.kspt.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

fun ViewGroup.inflate(resource: Int, attachToRoot: Boolean = false): View =
        LayoutInflater.from(context).inflate(resource, this, attachToRoot)

fun ViewGroup.children(): List<View> =
        (0 until childCount).map { getChildAt(it) }