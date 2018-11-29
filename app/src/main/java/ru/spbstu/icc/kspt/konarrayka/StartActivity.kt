package ru.spbstu.icc.kspt.konarrayka

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_start.*
import ru.spbstu.icc.kspt.konarrayka.R.id.recyclerView
import android.view.View
import ru.spbstu.icc.kspt.konarrayka.R.id.transition_position
import ru.spbstu.icc.kspt.konarrayka.R.id.recyclerView
import android.graphics.Point
import android.support.v7.widget.*
import java.security.AccessController.getContext
import ru.spbstu.icc.kspt.konarrayka.R.id.recyclerView




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
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)
        //val snapSnap = LinearSnapHelper()
        //snapSnap.attachToRecyclerView(recyclerView)
    }






    fun returnBack(@Suppress("UNUSED_PARAMETER") view: View) {

        val layoutManager = recyclerView.layoutManager as LinearLayoutManager?
        var activePosition = layoutManager!!.findFirstVisibleItemPosition()
        activePosition--
        recyclerView.scrollToPosition(activePosition)
        }


    fun returnNext(@Suppress("UNUSED_PARAMETER") view: View) {

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager?
            var activePosition = layoutManager!!.findFirstVisibleItemPosition()
            activePosition++
            recyclerView.smoothScrollBy(1080,0)

    }

}
