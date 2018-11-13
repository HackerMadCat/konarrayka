package ru.spbstu.icc.kspt.builder

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.DragEvent
import android.view.View
import kotlinx.android.synthetic.main.activity_builder.*
import ru.spbstu.icc.kspt.builder.adapters.ActionsAdapter
import ru.spbstu.icc.kspt.builder.adapters.ModelsAdapter
import ru.spbstu.icc.kspt.builder.adapters.RolesAdapter
import ru.spbstu.icc.kspt.builder.models.Action
import ru.spbstu.icc.kspt.builder.models.Model
import ru.spbstu.icc.kspt.builder.models.Role

class BuilderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_builder)
        val actions = generateActions(limit = 30)
        val roles = generateRoles(limit = 30)
        rv_actions.layoutManager = LinearLayoutManager(this)
        rv_roles.layoutManager = LinearLayoutManager(this)
        rv_models.layoutManager = LinearLayoutManager(this)
        rv_actions.adapter = ActionsAdapter(actions)
        rv_roles.adapter = RolesAdapter(roles)
        rv_models.adapter = ModelsAdapter(mutableListOf(), roles)
        rv_models.setOnDragListener { _, event ->
            val source = event.localState as View
            if (event.action == DragEvent.ACTION_DROP && source.id == R.id.tv_text) {
                val model = Model(actions[source.tag as Int], mutableListOf())
                (rv_models.adapter as ModelsAdapter).addModel(model)
            }
            true
        }
    }

    private fun generateActions(limit: Int) = (0..limit).map { Action("Action $it") }

    private fun generateColors(limit: Int): List<Int> {
        val palette = listOf(
                Color.CYAN,
                Color.MAGENTA,
                Color.BLUE,
                Color.GREEN,
                Color.RED,
                Color.YELLOW
        )
        return (0..limit).map { palette[it % palette.size] }
    }

    private fun generateRoles(limit: Int): List<Role> {
        val colors = generateColors(limit = limit)
        return (0..limit).map { Role("Role $it", colors[it]) }
    }
}