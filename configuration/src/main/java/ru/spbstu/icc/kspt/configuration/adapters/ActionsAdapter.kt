package ru.spbstu.icc.kspt.configuration.adapters

import android.app.Activity
import android.content.ClipData
import android.content.Intent
import android.os.Build
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.item_action.view.*
import ru.spbstu.icc.kspt.configuration.R
import ru.spbstu.icc.kspt.configuration.inflate
import ru.spbstu.icc.kspt.configuration.model.Action
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.spbstu.icc.kspt.common.toast
import ru.spbstu.icc.kspt.configuration.mutableModel.MutableRules
import ru.spbstu.icc.kspt.sound.SoundManager

typealias ActionVH = ActionsAdapter.ActionVH

// todo: [val activity: Activity] replace to [activity: Activity]
class ActionsAdapter(val rules: MutableRules, val activity: Activity) : Adapter<ActionVH>() {

    // SoundManager is a stub. todo: replace on ConfigurationManager.Action
    private val manager = SoundManager(activity)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ActionVH(parent.inflate(R.layout.item_action))

    override fun onBindViewHolder(holder: ActionVH, position: Int) =
            holder.bind(rules.actions[position])

    override fun getItemCount() = rules.actions.size

    inner class ActionVH(itemView: View) : ViewHolder(itemView), View.OnTouchListener {

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
            // todo: use ConfigurationManager.Action
            manager.record {
                activity.toast(it.file.name)
            }
        }
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        manager.onActivityResult(requestCode, resultCode, data)
    }
}
