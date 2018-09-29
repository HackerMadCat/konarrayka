package ru.spbstu.icc.kspt.common

import android.app.Activity
import android.content.Intent

inline fun <reified T> Intent.getExtra(name: String) = extras?.get(name) as T

inline fun <reified T> Activity.getExtra(name: String) = intent.getExtra<T>(name)
