package ru.spbstu.icc.kspt.configuration.adapters

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.RecyclerView
import android.view.DragEvent
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_model.view.*
import kotlinx.android.synthetic.main.layout_role.view.*
import ru.spbstu.icc.kspt.common.add
import ru.spbstu.icc.kspt.configuration.ConditionElement
import ru.spbstu.icc.kspt.configuration.R
import ru.spbstu.icc.kspt.configuration.inflate
import ru.spbstu.icc.kspt.configuration.model.Condition
import ru.spbstu.icc.kspt.configuration.model.ConditionalAction
import ru.spbstu.icc.kspt.configuration.model.Hero

class ConditionalActionsAdapter(
        private val conditionalActions: MutableList<ConditionalAction>,
        private val heroes: List<Hero>
) : RecyclerView.Adapter<ConditionalActionsAdapter.ConditionalActionVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConditionalActionVH =
            ConditionalActionVH(parent.inflate(R.layout.item_model))

    override fun onBindViewHolder(holder: ConditionalActionVH, position: Int) =
            holder.bind(conditionalActions[position])

    override fun getItemCount() = conditionalActions.size

    fun addConditionalAction(conditionalAction: ConditionalAction) {
        conditionalActions.add(conditionalAction)
        notifyItemInserted(conditionalActions.size)
    }

    inner class ConditionalActionVH(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnDragListener {
        override fun onDrag(v: View, event: DragEvent): Boolean {
            val source = event.localState as View
            if (event.action == DragEvent.ACTION_DROP && source.id == R.id.role_container) {
                val hero = heroes[source.tag as Int]
                val conditionalAction = conditionalActions[v.tag as Int]
                val condition = conditionalAction.condition
                val newConditionalAction = conditionalAction.copy(
                        condition = condition.addHero(hero)
                )
                conditionalActions[v.tag as Int] = newConditionalAction
                notifyItemChanged(v.tag as Int)
            }
            return true
        }

        fun bind(conditionalAction: ConditionalAction) {
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

    companion object {
        fun Condition.addOr(): Condition {
            return copy(disjunctions = disjunctions.add(setOf()))
        }

        fun Condition.addHero(hero: Hero): Condition {
            val conjunction = disjunctions.lastOrNull() ?: return Condition(listOf(setOf(hero)))
            val firstDisjunctions = disjunctions.dropLast(1)
            val newConjunction = conjunction.add(hero).toSet()
            val newDisjunctions = firstDisjunctions.add(newConjunction)
            return copy(disjunctions = newDisjunctions)
        }

        fun Condition.flatten(): List<ConditionElement> =
                disjunctions.map { conjunction ->
                    conjunction.map { hero ->
                        ConditionElement.HeroCE(hero)
                    }
                }.map {
                    it + listOf(ConditionElement.OrCE)
                }.flatten().dropLast(1)

    }
}
