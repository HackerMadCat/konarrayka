package ru.spbstu.icc.kspt.configuration

import ru.spbstu.icc.kspt.common.random
import ru.spbstu.icc.kspt.common.randomDrop
import ru.spbstu.icc.kspt.configuration.model.*
import java.io.File
import java.util.*
import java.util.logging.Level
import java.util.logging.Logger

object RandomModelGenerator {
    class RandomContext(val random: Random) {
        val usedStrings = HashSet<String>()
        val generatedFiles = HashSet<File>()
    }

    fun nextModel(random: Random): Pair<Model, Set<File>> {
        val context = RandomContext(random)
        val model = context.nextModel()
        val files = context.generatedFiles
        return model to files
    }

    private fun RandomContext.nextModel(): Model {
        val id = random.nextInt(1000)
        val icon = nextIcon()
        val name = random.nextString(10)
        val rules = nextRules()
        val sounds = nextRandomList(10) { nextSound() }
        val setups = nextRandomList(10) { nextSetup(rules.heroes) }
        return Model(id, icon, name, rules, sounds, setups)
    }

    private fun RandomContext.nextRules(): Rules {
        val heroes = nextRandomList(20) { nextHero() }.toSet()
        val actions = nextRandomList(20) { nextAction() }.toSet()
        val conditionalActions = nextRandomList(20) {
            nextConditionalAction(heroes, actions)
        }
        return Rules(heroes, actions, conditionalActions)
    }

    private fun RandomContext.nextConditionalAction(
            heroes: Set<Hero>,
            actions: Set<Action>
    ): ConditionalAction {
        val action = actions.random(random)!!
        val condition = nextCondition(heroes)
        return ConditionalAction(condition, action)
    }

    private fun RandomContext.nextCondition(heroes: Set<Hero>): Condition {
        val disjunctions = nextRandomList(3) { heroes.randomDrop(random).toSet() }
        return Condition(disjunctions)
    }

    private fun <T> RandomContext.nextRandomList(bound: Int, builder: () -> T): List<T> {
        return (0..random.nextNaturalInt(bound)).map { builder() }
    }

    private fun RandomContext.nextAction(): Action {
        val icon = nextIcon()
        val name = nextUniqueString(10)
        val description = random.nextString(100)
        val voices = nextRandomList(10) { nextSound() }
        val duration = nextDuration()
        return Action(icon, name, description, voices, duration)
    }

    private fun RandomContext.nextHero(): Hero {
        val name = nextUniqueString(10)
        val icon = nextIcon()
        val color = nextColor()
        return Hero(icon, name, color).apply {
            val logger = Logger.getLogger("RandomModelGenerator")
            logger.log(Level.SEVERE, toString())
        }
    }

    private fun RandomContext.nextColor(): Int {
        return -random.nextInt(0x1000000)
    }

    private fun RandomContext.nextSetup(heroes: Set<Hero>): Setup {
        val numPlayers = random.nextNaturalInt(heroes.size)
        return Setup(numPlayers, heroes)
    }

    private fun RandomContext.nextDuration(): Duration {
        val time = Duration.Time(random.nextNaturalInt(3600))
        val name = random.nextString(3)
        return when (random.nextInt(10)) {
            1 -> Duration.Sound
            in 2..4 -> time
            else -> Duration.Variable(name, time)
        }
    }

    private fun RandomContext.nextSound(): File {
        return nextFile("mp3")
    }

    private fun RandomContext.nextIcon(): File {
        return nextFile("png")
    }

    private fun RandomContext.nextFile(extension: String): File {
        val file = File(nextUniqueString(10) + "." + extension)
        generatedFiles.add(file)
        return file
    }

    private fun RandomContext.nextUniqueString(bound: Int): String {
        while (true) {
            val string = random.nextString(bound)
            if (usedStrings.contains(string)) continue
            usedStrings.add(string)
            return string
        }
    }

    private val ALPHA = ('a'..'z').toList()
    private val BIG_ALPHA = ('A'..'Z').toList()
    private val NUM = ('1'..'2').toList()
    private val ALPHA_NUM = ALPHA + BIG_ALPHA + NUM

    private fun Random.nextNaturalInt(bound: Int): Int = nextInt(bound - 1) + 1

    private fun Random.nextChar() = ALPHA_NUM.random(this)!!

    private fun Random.nextString(bound: Int): String {
        val length = nextNaturalInt(bound)
        return (0..length).map { nextChar() }.joinToString("")
    }
}