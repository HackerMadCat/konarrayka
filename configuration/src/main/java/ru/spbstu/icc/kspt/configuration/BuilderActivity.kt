package ru.spbstu.icc.kspt.configuration

import android.graphics.Color
import android.os.Bundle
import android.view.DragEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_builder.*
import ru.spbstu.icc.kspt.configuration.adapters.ActionsAdapter
import ru.spbstu.icc.kspt.configuration.adapters.ModelsAdapter
import ru.spbstu.icc.kspt.configuration.adapters.RolesAdapter
import ru.spbstu.icc.kspt.ui.models.Action
import ru.spbstu.icc.kspt.ui.models.CompositeRole
import ru.spbstu.icc.kspt.ui.models.Model

class BuilderActivity : AppCompatActivity() {

    private var lastActions = mutableListOf<() -> Unit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_builder)
        val actions = (0..30).map { Action("Action $it") }.toMutableList()
        val colors = listOf(
                Color.CYAN,
                Color.MAGENTA,
                Color.BLUE,
                Color.GREEN,
                Color.RED,
                Color.YELLOW
        )
        val roles = (mutableListOf(CompositeRole.Condition) + (0..30).map { CompositeRole.Role("Role $it", colors[it % colors.size]) }).toMutableList()
        val models = mutableListOf<Model>()
        rv_actions.layoutManager = LinearLayoutManager(this)
        rv_actions.adapter = ru.spbstu.icc.kspt.configuration.adapters.ActionsAdapter(actions, lastActions)
        rv_heroes.layoutManager = LinearLayoutManager(this)
        rv_heroes.adapter = RolesAdapter(roles, lastActions)
        rv_models.layoutManager = LinearLayoutManager(this)
        rv_models.adapter = ModelsAdapter(models, roles, lastActions)
        rv_models.setOnDragListener { v, event ->
            val source = event.localState as View
            if (event.action == DragEvent.ACTION_DROP && source.id == R.id.tv_action) {
                val model = Model(actions[source.tag as Int], mutableListOf())
                (rv_models.adapter as ModelsAdapter).addModel(model)
                lastActions.add {
                    models.removeAt(models.size - 1)
                    (rv_models.adapter)!!.notifyDataSetChanged()
                }
            }
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.action_undo) {
            if (lastActions.size > 0) {
                val undoAction = lastActions.removeAt(lastActions.size - 1)
                undoAction()
            }
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }
}