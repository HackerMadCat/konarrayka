package ru.spbstu.icc.kspt

fun main(args: Array<String>) {
    println("hello world")
    val configurationManager = ConfigurationManagerImpl()
    val storageManager = StorageManagerImpl()
    val file = storageManager.findFile()
    val model = configurationManager.loadModel(file)
    val scenario = configurationManager.getScenario(model)
    for (action in scenario.getActions()) {
        println("-----------------")
        println(action.getRoles().joinToString(" and ") { it.getName() })
        println(action.getText())
    }
}
