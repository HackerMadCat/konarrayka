package ru.spbstu.icc.kspt.configuration

import ru.spbstu.icc.kspt.model.Action
import ru.spbstu.icc.kspt.model.Model
import ru.spbstu.icc.kspt.model.Role
import ru.spbstu.icc.kspt.model.Scenario
import java.io.File

class ConfigurationManager {
    fun loadModel(file: File): Model {
        return Model().apply {
            val hunter = Role("Bruno")
            val rabbit1 = Role("Mike")
            val rabbit2 = Role("April")
            val everyOne = Role("every one")
            addRole(hunter)
            addRole(rabbit1)
            addRole(rabbit2)
            addAction(Action(rabbit1, rabbit2, text = """
                Rabbits, opens their eyes.
                Choose a place in which the hunter will not find us.
                Moves his rabbit marker and closes his eyes.
            """.trimIndent()))
            addAction(Action(hunter, text = """
                Hunter, opens his eyes.
                Choose the place where the rabbit is.
                Moves his bullet marker and closes his eyes.
            """.trimIndent()))
            addAction(Action(everyOne, text = """
                Everyone, opened their eyes.
            """.trimIndent()))
            addAction(Action(rabbit1, rabbit2, text = """
                Rabbits, check their holes.
                If we have a bullet marker in self hole, then we will dead and lose the game.
            """.trimIndent()))
        }
    }

    fun getScenario(model: Model) = Scenario(model.getActions())
}
