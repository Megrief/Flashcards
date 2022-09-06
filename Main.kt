package flashcards

import kotlin.random.Random
val random = Random.Default
fun String.addToLog(): String {
    logList.add(this)
    return this
}
val logList = mutableListOf<String>()

fun main(args: Array<String>) {
    var imp = " "
    var exp = " "
    for (str in args) {
        if (str == "-import") imp = args[args.indexOf(str) + 1]
        if (str == "-export") exp = args[args.indexOf(str) + 1]
    }
    Flashcards(imp, exp)
}