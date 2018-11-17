package ru.spbstu.icc.kspt.configuration

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.DragEvent
import android.view.View
import kotlinx.android.synthetic.main.activity_builder.*
import ru.spbstu.icc.kspt.configuration.adapters.ActionsAdapter
import ru.spbstu.icc.kspt.configuration.adapters.ConditionalActionsAdapter
import ru.spbstu.icc.kspt.configuration.adapters.ConditionElementAdapter
import ru.spbstu.icc.kspt.configuration.model.Condition
import ru.spbstu.icc.kspt.configuration.model.ConditionalAction
import java.util.*
import kotlin.collections.ArrayList

class BuilderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_builder)
        val (model, _) = RandomModelGenerator.nextModel(Random())
        val actions = model.rules.actions.toList()
        val heroes = model.rules.heroes.toList()
        val conditionalActions = ArrayList<ConditionalAction>()//model.rules.conditionalActions.toList()
        val conditionElements = listOf(ConditionElement.OrCE) + heroes.map { ConditionElement.HeroCE(it) }
        rv_actions.layoutManager = LinearLayoutManager(this)
        rv_roles.layoutManager = LinearLayoutManager(this)
        rv_models.layoutManager = LinearLayoutManager(this)
        rv_actions.adapter = ActionsAdapter(actions)
        rv_roles.adapter = ConditionElementAdapter(conditionElements)
        rv_models.adapter = ConditionalActionsAdapter(conditionalActions, heroes)
        rv_models.setOnDragListener { _, event ->
            val source = event.localState as View
            if (event.action == DragEvent.ACTION_DROP && source.id == R.id.tv_text) {
                val action = actions[source.tag as Int]
                val condition = Condition(emptyList())
                val conditionalAction = ConditionalAction(condition, action)
                val conditionalActionsAdapter = rv_models.adapter as ConditionalActionsAdapter
                conditionalActionsAdapter.addConditionalAction(conditionalAction)
            }
            true
        }
    }
}