package ru.spbstu.icc.kspt.configuration.adapters

import android.app.Activity
import android.content.ClipData
import android.content.Intent
import android.os.Build
import android.view.*
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_builder.view.*
import kotlinx.android.synthetic.main.item_action.view.*
import ru.spbstu.icc.kspt.configuration.R
import ru.spbstu.icc.kspt.configuration.children
import ru.spbstu.icc.kspt.configuration.inflate
import ru.spbstu.icc.kspt.configuration.manager.ActionManager
import ru.spbstu.icc.kspt.ui.models.Action
import kotlin.math.abs

class ActionsAdapter(
        private val actions: MutableList<Action>,
        private val lastActions: MutableList<() -> Unit>,
        activity: Activity
) : RecyclerView.Adapter<ActionsAdapter.ActionVH>() {

    private val actionManager = ActionManager(activity)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActionVH {
        val itemView = parent.inflate(R.layout.item_action)
        return ActionVH(itemView)
    }

    override fun onBindViewHolder(holder: ActionVH, position: Int) = holder.bind(actions[position])

    override fun getItemCount() = actions.size

    inner class ActionVH(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnTouchListener {

        private val gestureListener = object : GestureDetector.SimpleOnGestureListener() {
            /**
             * Method that change columns weights
             */
            override fun onDoubleTap(e: MotionEvent): Boolean {
                val listsContainer = itemView.parent.parent as ViewGroup
                val controlsContainer = (listsContainer.parent as ViewGroup).controls_container
                val buttons = controlsContainer.children()
                val rvActions = listsContainer.rv_actions
                val rvHeroes = listsContainer.rv_heroes
                val rvModels = listsContainer.rv_models
                val recyclerViews = listOf(rvActions, rvHeroes, rvModels)
                val weights = if ((rvActions.layoutParams as LinearLayout.LayoutParams).weight == 6.0F) {
                    listOf(1.5F, 6.0F, 1.5F)
                } else {
                    listOf(6.0F, 1.5F, 1.5F)
                }
                weights.forEachIndexed { index, weight ->
                    var lp = recyclerViews[index].layoutParams as LinearLayout.LayoutParams
                    lp.weight = weight
                    recyclerViews[index].layoutParams = lp
                    lp = buttons[index].layoutParams as LinearLayout.LayoutParams
                    lp.weight = weight
                    buttons[index].layoutParams = lp
                }
                return true
            }

            /**
             * Method for removing action item from list, if diff between start and end point more
             * that 0.5 of item width
             */
            override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
                val dx = abs(e2.x - e1.x)
                if (itemView.width / dx > 0.5) {
                    val action = actions.removeAt(adapterPosition)
                    val position = adapterPosition
                    notifyItemRemoved(position)
                    lastActions.add {
                        actions.add(position, action)
                        notifyItemInserted(position)
                    }
                }
                return true
            }

            /**
             * Method for displaying dialog with action information
             */
            override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
                actionManager.configureAction {
                    println(it)
                }
                return true
            }

            /**
             * Method that start drag operation
             */
            override fun onLongPress(e: MotionEvent) {
                val clipData = ClipData.newPlainText("", "")
                val shadowBuilder = View.DragShadowBuilder(itemView)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    itemView.startDragAndDrop(clipData, shadowBuilder, itemView, 0)
                } else {
                    itemView.startDrag(clipData, shadowBuilder, itemView, 0)
                }
            }

            override fun onDown(e: MotionEvent): Boolean {
                return true
            }
        }

        private val gestureDetector = GestureDetector(itemView.context, gestureListener)

        /**
         * Method that set action item height as 7/8 * a
         * a = 1/16 of screen height
         */
        private fun changeHeight() {
            val screenHeight = itemView.resources.displayMetrics.heightPixels;
            val a = screenHeight / 16.0F
            val measuredHeight = 7 * a / 8
            val lp = itemView.layoutParams
            lp.height = measuredHeight.toInt()
            itemView.layoutParams = lp
        }

        fun bind(action: Action) {
            with(itemView) {
                changeHeight()
                tv_action.text = action.title
                tv_action.tag = adapterPosition
                setOnTouchListener(this@ActionVH)
                setOnDragListener { v, event ->
                    val source = event.localState as View
                    if (event.action == DragEvent.ACTION_DROP && source.id == R.id.tv_action) {
                        val from = source.tag as Int
                        val to = adapterPosition
                        val tmp = actions[from]
                        actions[from] = actions[to]
                        actions[to] = tmp
                        notifyDataSetChanged()
                        lastActions.add {
                            val tmp = actions[from]
                            actions[from] = actions[to]
                            actions[to] = tmp
                            notifyDataSetChanged()
                        }
                    }
                    true
                }
            }
        }

        /**
         * Method for sending touch events to gesture detector
         */
        override fun onTouch(v: View, event: MotionEvent): Boolean {
            return gestureDetector.onTouchEvent(event)
        }
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        actionManager.onActivityResult(requestCode, resultCode, data)
    }
}