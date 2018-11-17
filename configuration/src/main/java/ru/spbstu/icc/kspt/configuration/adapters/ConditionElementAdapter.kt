package ru.spbstu.icc.kspt.configuration.adapters

import android.content.ClipData
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.support.v7.widget.RecyclerView
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.item_role.view.*
import ru.spbstu.icc.kspt.configuration.ConditionElement
import ru.spbstu.icc.kspt.configuration.R
import ru.spbstu.icc.kspt.configuration.inflate

class ConditionElementAdapter(
        private val conditionElements: List<ConditionElement>
) : RecyclerView.Adapter<ConditionElementAdapter.HeroVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            HeroVH(parent.inflate(R.layout.item_role))

    override fun onBindViewHolder(holder: HeroVH, position: Int) =
            holder.bind(conditionElements[position])

    override fun getItemCount() = conditionElements.size

    class HeroVH(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnTouchListener {

        private val gestureListener = object : GestureDetector.SimpleOnGestureListener() {

            override fun onDoubleTap(event: MotionEvent): Boolean {
                changeColumnWidth()
                return true
            }

            override fun onSingleTapConfirmed(event: MotionEvent): Boolean {
                showEditRoleDialog()
                return true
            }

            override fun onLongPress(event: MotionEvent) {
                prepareAndStartDrag()
            }

            override fun onDown(event: MotionEvent) = true
        }

        private val gestureDetector = GestureDetector(itemView.context, gestureListener)

        override fun onTouch(v: View, event: MotionEvent) = gestureDetector.onTouchEvent(event)

        fun bind(conditionElement: ConditionElement) {
            with(itemView) {
                val color = when (conditionElement) {
                    is ConditionElement.HeroCE -> conditionElement.hero.color
                    is ConditionElement.OrCE -> Color.GRAY
                }
                val name = when (conditionElement) {
                    is ConditionElement.HeroCE -> conditionElement.hero.name
                    is ConditionElement.OrCE -> "OR"
                }
                view_badge.background = ColorDrawable(color)
                tv_name.text = name
                tag = adapterPosition
                setOnTouchListener(this@HeroVH)
            }
        }

        private fun prepareAndStartDrag() {
            val clipData = ClipData.newPlainText("", "")
            val shadowBuilder = View.DragShadowBuilder(itemView)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                itemView.startDragAndDrop(clipData, shadowBuilder, itemView, 0)
            } else {
                itemView.startDrag(clipData, shadowBuilder, itemView, 0)
            }
        }

        private fun changeColumnWidth() {
            val lp = (itemView.parent as ViewGroup).layoutParams as LinearLayout.LayoutParams
            if (lp.weight.toInt() == 1) {
                lp.weight = 2.0F
            } else {
                lp.weight = 1.0F
            }
            (itemView.parent as ViewGroup).layoutParams = lp
        }

        private fun showEditRoleDialog() {
            TODO()
        }
    }
}
