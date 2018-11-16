package ru.spbstu.icc.kspt.configuration.adapters

import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.RecyclerView
import android.view.DragEvent
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_model.view.*
import kotlinx.android.synthetic.main.item_role.view.*
import ru.spbstu.icc.kspt.configuration.R
import ru.spbstu.icc.kspt.configuration.inflate
import ru.spbstu.icc.kspt.configuration.models.Model
import ru.spbstu.icc.kspt.configuration.models.Role

class ModelsAdapter(private val models: MutableList<Model>, private val roles: List<Role>) : RecyclerView.Adapter<ModelsAdapter.ModelVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModelVH = ModelVH(parent.inflate(R.layout.item_model))

    override fun onBindViewHolder(holder: ModelVH, position: Int) = holder.bind(models[position])

    override fun getItemCount() = models.size

    fun addModel(model: Model) {
        models.add(model)
        notifyItemInserted(models.size)
    }

    inner class ModelVH(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnDragListener {
        override fun onDrag(v: View, event: DragEvent): Boolean {
            val source = event.localState as View
            if (event.action == DragEvent.ACTION_DROP && source.id == R.id.role_container) {
                val role = roles[source.tag as Int]
                val model = models[v.tag as Int]
                models[v.tag as Int] = model.copy(roles = listOf(*model.roles.toTypedArray(), role))
                notifyItemChanged(v.tag as Int)
            }
            return true
        }

        fun bind(model: Model) {
            with(itemView) {
                tv_action.text = model.action.text
                roles_container.removeAllViews()
                model.roles.takeLast(3).forEach { action ->
                    val roleView = roles_container.inflate(R.layout.layout_role)
                    with(roleView) {
                        view_badge.background = ColorDrawable(action.color)
                    }
                    roles_container.addView(roleView)
                }
                tag = adapterPosition
                setOnDragListener(this@ModelVH)
            }
        }
    }
}