package ru.spbstu.icc.kspt.configuration.adapters

import android.content.ClipData
import android.os.Build
import android.support.v7.widget.RecyclerView
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.item_action.view.*
import ru.spbstu.icc.kspt.configuration.R
import ru.spbstu.icc.kspt.configuration.inflate
import ru.spbstu.icc.kspt.configuration.model.Action

class ActionsAdapter(private val actions: List<Action>) : RecyclerView.Adapter<ActionsAdapter.ActionVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ActionVH(parent.inflate(R.layout.item_action))

    override fun onBindViewHolder(holder: ActionVH, position: Int) =
            holder.bind(actions[position])

    override fun getItemCount() = actions.size

    class ActionVH(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnTouchListener {

        private val gestureListener = object : GestureDetector.SimpleOnGestureListener() {

            override fun onDoubleTap(event: MotionEvent): Boolean {
                changeColumnWidth()
                return true
            }

            override fun onSingleTapConfirmed(event: MotionEvent): Boolean {
                showEditActionDialog()
                return true
            }

            override fun onLongPress(event: MotionEvent) {
                prepareAndStartDrag()
            }

            override fun onDown(event: MotionEvent) = true
        }

        private val gestureDetector = GestureDetector(itemView.context, gestureListener)

        override fun onTouch(v: View, event: MotionEvent) = gestureDetector.onTouchEvent(event)

        fun bind(action: Action) {
            with(itemView) {
                tv_text.text = action.name
                tv_text.tag = adapterPosition
                setOnTouchListener(this@ActionVH)
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

        private fun showEditActionDialog() {
            TODO()
        }
    }
}
