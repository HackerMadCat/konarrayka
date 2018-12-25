package ru.spbstu.icc.kspt.konarrayka

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.PagerSnapHelper
import kotlinx.android.synthetic.main.activity_start.*


class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        val posts: ArrayList<String> = ArrayList()
        posts.add("Game 1")
        posts.add("Game 2")
        posts.add("Game 3")
        posts.add("Game 4")
        posts.add("Game 5")
        posts.add("New Game")

        recyclerView.layoutManager = LinearLayoutManager(this, OrientationHelper.HORIZONTAL, false)
        recyclerView.adapter = PostsAdapter(posts)
        recyclerView.addItemDecoration(LinePagerIndicatorDecoration());
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)
    }

    fun returnBack(@Suppress("UNUSED_PARAMETER") view: View) {
        recyclerView.smoothScrollBy(-1080, 0)
    }

    fun returnNext(@Suppress("UNUSED_PARAMETER") view: View) {
        recyclerView.smoothScrollBy(1080, 0)
    }
}
