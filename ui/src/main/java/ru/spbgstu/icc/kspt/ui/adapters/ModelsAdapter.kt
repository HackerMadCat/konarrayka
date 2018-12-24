package ru.spbgstu.icc.kspt.ui.adapters

import android.content.ClipData
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.*
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItems
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.item_action.view.*
import kotlinx.android.synthetic.main.item_model.view.*
import ru.spbgstu.icc.kspt.ui.R
import ru.spbgstu.icc.kspt.ui.children
import ru.spbgstu.icc.kspt.ui.inflate
import ru.spbgstu.icc.kspt.ui.models.CompositeRole
import ru.spbgstu.icc.kspt.ui.models.Model
import kotlin.math.abs

class ModelsAdapter(
        private val models: MutableList<Model>,
        private val roles: List<CompositeRole>,
        private val lastActions: MutableList<() -> Unit>
) : RecyclerView.Adapter<ModelsAdapter.ModelVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModelVH {
        val itemVIew = parent.inflate(R.layout.item_model)
        return ModelVH(itemVIew)
    }

    override fun onBindViewHolder(holder: ModelVH, position: Int) = holder.bind(models[position])

    override fun getItemCount() = models.size

    fun addModel(model: Model) {
        models.add(model)
        notifyItemInserted(models.size)
    }

    inner class ModelVH(itemVIew: View) : RecyclerView.ViewHolder(itemVIew), View.OnDragListener, View.OnTouchListener {
        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
            return gestureDetector.onTouchEvent(event)
        }

        private val gestureListener = object : GestureDetector.SimpleOnGestureListener() {
            override fun onLongPress(e: MotionEvent) {
                val clipData = ClipData.newPlainText("", "")
                val shadowBuilder = View.DragShadowBuilder(itemView)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    itemView.startDragAndDrop(clipData, shadowBuilder, itemView, 0)
                } else {
                    itemView.startDrag(clipData, shadowBuilder, itemView, 0)
                }
            }

            override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                val model = models[adapterPosition]
                MaterialDialog(itemVIew.context)
                        .title(text = model.action.title)
                        .show {
                            val items = model.roles.map {
                                when (it) {
                                    is CompositeRole.Role -> it.name
                                    is CompositeRole.Condition -> "OR"
                                }
                            }
                            listItems(items = items)
                        }
                return true
            }

            override fun onDoubleTap(e: MotionEvent): Boolean {
                val listsContainer = itemView.parent.parent as ViewGroup
                val controlsContainer = (listsContainer.parent as ViewGroup).controls_container
                val buttons = controlsContainer.children()
                val rvActions = listsContainer.rv_actions
                val rvHeroes = listsContainer.rv_heroes
                val rvModels = listsContainer.rv_models
                val recyclerViews = listOf(rvActions, rvHeroes, rvModels)
                val weights = if ((rvModels.layoutParams as LinearLayout.LayoutParams).weight == 6.0F) {
                    listOf(1.5F, 6.0F, 1.5F)
                } else {
                    listOf(1.5F, 1.5F, 6.0F)
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

            override fun onDown(e: MotionEvent): Boolean {
                return true
            }

            override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
                val dx = abs(e2.x - e1.x)
                if (itemView.width / dx > 0.5) {
                    val action = models.removeAt(adapterPosition)
                    val position = adapterPosition
                    notifyItemRemoved(position)
                    lastActions.add {
                        models.add(position, action)
                        notifyItemInserted(position)
                    }
                }
                return true
            }
        }

        private val gestureDetector = GestureDetector(itemView.context, gestureListener)

        override fun onDrag(v: View, event: DragEvent): Boolean {
            val source = event.localState as View
            if (event.action == DragEvent.ACTION_DROP && source.id == R.id.role_container) {
                val role = roles[source.tag as Int]
                val roles = models[v.tag as Int].roles
                if (!(roles.isNotEmpty() && roles.last() is CompositeRole.Condition && role is CompositeRole.Condition)) {
                    models[v.tag as Int].roles.add(role)
                    notifyItemChanged(v.tag as Int)
                    lastActions.add {
                        models[v.tag as Int].roles.removeAt(models[v.tag as Int].roles.size - 1)
                        notifyItemChanged((v.tag as Int))
                    }
                }
            }
            if (event.action == DragEvent.ACTION_DROP && source.id == R.id.model_container) {
                val from = source.tag as Int
                val to = adapterPosition
                val tmp = models[from]
                models[from] = models[to]
                models[to] = tmp
                notifyDataSetChanged()
                lastActions.add {
                    models[from] = models[to]
                    models[to] = tmp
                    notifyDataSetChanged()
                }
            }
            return true
        }

        private fun changeHeight() {
            val screenHeight = itemView.resources.displayMetrics.heightPixels;
            val a = screenHeight / 16.0F
            val measuredHeight = 7 * a / 8
            val lp = itemView.layoutParams
            lp.height = measuredHeight.toInt()
            itemView.layoutParams = lp
        }

        fun bind(model: Model) {
            with(itemView) {
                changeHeight()
                tv_role.text = model.action.title
                actions_container.removeAllViews()
                model.roles.takeLast(3).forEach { role ->
                    val actionView = LayoutInflater.from(context).inflate(R.layout.layout_action, actions_container, false)
                    with(actionView) {
                        when (role) {
                            is CompositeRole.Role -> {
                                tv_action.background = ColorDrawable(role.color)
                            }
                            is CompositeRole.Condition -> {
                                tv_action.background = ColorDrawable(Color.BLACK)
                            }
                        }
                    }
                    actions_container.addView(actionView)
                }
                tag = adapterPosition
                setOnDragListener(this@ModelVH)
                setOnTouchListener(this@ModelVH)
            }
        }
    }
}