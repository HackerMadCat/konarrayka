package ru.spbstu.icc.kspt.konarrayka

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_start.*
import android.view.View
import android.support.v7.widget.*
import android.view.Menu
import android.view.MenuItem
import android.widget.ActionMenuView
import android.widget.Toast
import ru.spbstu.icc.kspt.konarrayka.R.layout.mainsettings_activity

class StartActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        val posts: ArrayList<String> = ArrayList()

        for (i in 1..5) {
            posts.add("Game $i")
        }


        posts.add("New Game")

        recyclerView.layoutManager = LinearLayoutManager(this, OrientationHelper.HORIZONTAL, false) as RecyclerView.LayoutManager?
        recyclerView.adapter = PostsAdapter(posts)
        recyclerView.addItemDecoration(LinePagerIndicatorDecoration());
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if(item.itemId == R.id.action_settings) {
            startActivity(Intent(this, MainSettings::class.java))
            true
        }
        else super.onOptionsItemSelected(item)
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
