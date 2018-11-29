package ru.spbstu.icc.kspt.configuration.adapters

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.RecyclerView.Adapter
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.DragEvent
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_model.view.*
import kotlinx.android.synthetic.main.layout_role.view.*
import ru.spbstu.icc.kspt.configuration.ConditionElement
import ru.spbstu.icc.kspt.configuration.ConditionElement.*
import ru.spbstu.icc.kspt.configuration.R
import ru.spbstu.icc.kspt.configuration.inflate
import ru.spbstu.icc.kspt.configuration.mutableModel.MutableConditionalAction
import ru.spbstu.icc.kspt.configuration.mutableModel.MutableRules

typealias ConditionalActionVH = ConditionalActionsAdapter.ConditionalActionVH

class ConditionalActionsAdapter(val rules: MutableRules) : Adapter<ConditionalActionVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConditionalActionVH =
            ConditionalActionVH(parent.inflate(R.layout.item_model))

    override fun onBindViewHolder(holder: ConditionalActionVH, position: Int) =
            holder.bind(rules.conditionalActions[position])

    override fun getItemCount() = rules.conditionalActions.size

    fun addConditionalAction(conditionalAction: MutableConditionalAction) {
        rules.conditionalActions.add(conditionalAction)
        notifyItemInserted(rules.conditionalActions.size)
    }

    inner class ConditionalActionVH(itemView: View) : ViewHolder(itemView), View.OnDragListener {
        override fun onDrag(v: View, event: DragEvent): Boolean {
            val source = event.localState as View
            if (event.action == DragEvent.ACTION_DROP && source.id == R.id.role_container) {
                val conditionElement = rules.conditionElements[source.tag as Int]
                val conditionalAction = rules.conditionalActions[v.tag as Int]
                val condition = conditionalAction.condition
                when (conditionElement) {
                    is HeroCE -> condition.addHero(conditionElement.hero)
                    is OrCE -> condition.addOr()
                }
                notifyItemChanged(v.tag as Int)
            }
            return true
        }

        fun bind(conditionalAction: MutableConditionalAction) {
            with(itemView) {
                tv_action.text = conditionalAction.action.name
                roles_container.removeAllViews()
                for (conditionElement in conditionalAction.condition.flatten().takeLast(3)) {
                    val roleView = roles_container.inflate(R.layout.layout_role)
                    val color = when (conditionElement) {
                        is ConditionElement.HeroCE -> conditionElement.hero.color
                        is ConditionElement.OrCE -> Color.GRAY
                    }
                    roleView.view_role.background = ColorDrawable(color)
                    roles_container.addView(roleView)
                }
                tag = adapterPosition
                setOnDragListener(this@ConditionalActionVH)
            }
        }
    }
}
