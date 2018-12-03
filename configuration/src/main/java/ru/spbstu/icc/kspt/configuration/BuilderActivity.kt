package ru.spbstu.icc.kspt.configuration

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.DragEvent
import android.view.View
import kotlinx.android.synthetic.main.activity_builder.*
import ru.spbstu.icc.kspt.configuration.adapters.ActionsAdapter
import ru.spbstu.icc.kspt.configuration.adapters.ConditionalActionsAdapter
import ru.spbstu.icc.kspt.configuration.adapters.ConditionElementAdapter
import ru.spbstu.icc.kspt.configuration.mutableModel.MutableCondition
import ru.spbstu.icc.kspt.configuration.mutableModel.MutableConditionalAction
import ru.spbstu.icc.kspt.configuration.mutableModel.asMutable
import java.util.*

class BuilderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_builder)
        val (model, _) = RandomModelGenerator.nextModel(Random())
        val mutableModel = model.asMutable()
        val mutableRules = mutableModel.rules
        rv_actions.layoutManager = LinearLayoutManager(this)
        rv_roles.layoutManager = LinearLayoutManager(this)
        rv_models.layoutManager = LinearLayoutManager(this)
        rv_actions.adapter = ActionsAdapter(mutableRules, this)
        rv_roles.adapter = ConditionElementAdapter(mutableRules, this)
        rv_models.adapter = ConditionalActionsAdapter(mutableRules)
        rv_models.setOnDragListener { _, event ->
            val source = event.localState as View
            if (event.action == DragEvent.ACTION_DROP && source.id == R.id.tv_text) {
                val action = mutableRules.actions[source.tag as Int]
                val condition = MutableCondition(ArrayList())
                val conditionalAction = MutableConditionalAction(condition, action)
                val conditionalActionsAdapter = rv_models.adapter as ConditionalActionsAdapter
                conditionalActionsAdapter.addConditionalAction(conditionalAction)
            }
            true
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val actionAdapter = rv_actions.adapter as ActionsAdapter
        actionAdapter.onActivityResult(requestCode, resultCode, data)
        val conditionAdapter = rv_roles.adapter as ConditionElementAdapter
        conditionAdapter.onActivityResult(requestCode, resultCode, data)
    }
}