package ru.spbstu.icc.kspt.konarrayka

import android.os.Bundle
import android.app.Activity
import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val posts: ArrayList<String> = ArrayList()

        for (i in 1..5){
            posts.add("Game $i")
        }

        posts.add("New Game")

        val buttonNext: Button;


        recyclerView.layoutManager = LinearLayoutManager(this, OrientationHelper.HORIZONTAL, false) as RecyclerView.LayoutManager?
        recyclerView.adapter = PostsAdapter(posts)
    }

}
