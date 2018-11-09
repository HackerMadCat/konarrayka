package ru.spbstu.icc.kspt.konarrayka

import android.annotation.SuppressLint
import android.content.Context
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

class ViewPagerAdapter : PagerAdapter() {

    lateinit var context : Context
    private var images = arrayOf(R.drawable.slide1,R.drawable.slide2,R.drawable.slide3)



    fun ViewPagerAdapter(context:Context){
        this.context = context
    }


    override fun isViewFromObject(p0: View, p1: Any): Boolean {
        return p0 == p1
    }

    override fun getCount(): Int {
        return images.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): View {
        val layoutInflater : LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater;
        val view : View = layoutInflater.inflate(R.layout.custom_layout, null)
        val imageView : ImageView = view.findViewById(R.id.imageView)
        imageView.setImageResource(images[position])



        val vp : ViewPager = container as ViewPager
        vp.addView(view, 0)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val vp : ViewPager =  container as ViewPager
        val view : View = `object` as View
        vp.removeView(view)
    }
}


