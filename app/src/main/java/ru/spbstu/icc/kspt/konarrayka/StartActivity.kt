package ru.spbstu.icc.kspt.konarrayka

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_start.*


class StartActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        val posts: ArrayList<String> = ArrayList()

        for (i in 1..5){
            posts.add("Game $i")
        }


        posts.add("New Game")

        recyclerView.layoutManager = LinearLayoutManager(this, OrientationHelper.HORIZONTAL, false) as RecyclerView.LayoutManager?
        recyclerView.adapter = PostsAdapter(posts)
        recyclerView.addItemDecoration(LinePagerIndicatorDecoration());

    }



}
