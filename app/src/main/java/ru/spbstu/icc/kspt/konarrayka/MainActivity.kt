package ru.spbstu.icc.kspt.konarrayka


import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager  = findViewById<View>(R.id.viewPager) as ViewPager

        lateinit var viewPagerAdapter : ViewPagerAdapter

        viewPager.adapter = viewPagerAdapter

    }
    }