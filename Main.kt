package flashcards

import java.io.File
import java.io.FileNotFoundException
import java.util.Scanner
val scan = Scanner(System.`in`)

fun main() {
    Flashcards()
}

class Flashcards {
    private val cards = mutableMapOf<String, String>()
    private var stop = false
    init {
        while (!stop) {
            menu()
        }
    }

    private fun menu() {
        println("Input the action (add, remove, import, export, ask, exit):")
        val input = readln()
        if (input != "exit") {
            when (input) {
                "add" -> adding()
                "remove" -> removing()
                "import" -> importing()
                "export" -> exporting()
                "ask" -> asking()
                else -> println("Wrong command!")
            }
        } else exit()
    }
    private fun adding() {
        println("The card:")
        val key: String = readln()
        try {
            if (key in cards.keys) throw Exception("Key exists")
        } catch (e: Exception) {
            println("The card \"$key\" already exists.")
            return menu()
        }
        println("The definition of the card:")
        val value: String = readln()
        try {
            if (value in cards.values) throw Exception("Value exists")
        } catch (e: Exception) {
            println("The definition \"$value\" already exists.")
            return menu()
        }
        cards[key] = value
        println("The pair (\"$key\":\"$value\") has been added.")
        return menu()
    }
    private fun removing() {
        println("Which card?")
        val input = readln()
        if (input in cards.keys) {
            cards.remove(input)
            println("The card has been removed.")
        } else println("Can't remove \"$input\": there is no such card.")
        menu()
    }
    private fun importing() {
        println("File name:")
        try {
            val file = File(readln())
            val list: List<String> = file.readLines()
            val map: Map<String, String> = buildMap {
                list.forEach {
                    val li = it.split(':')
                    this[li[0]] = li[1]
                }
            }
            cards += map
            println(map.size.toString() + " cards have been loaded." )
        } catch (e: FileNotFoundException) {
            println("File not found.")
        }
        menu()
    }
    private fun exporting() {
        try {
            println("File name:")
            val savedCards = File(readln())
            val list = buildList {
                cards.forEach { (key, value) ->
                    this.add("$key:$value")
                }
            }
            for (ind in list.indices) {
                if (ind == 0) {
                    savedCards.writeText("""
                    ${ list[ind] }
                    
                    """.trimIndent()
                    )
                } else {
                    savedCards.appendText("""
                    ${ list[ind] }
                    
                    """.trimIndent()
                    )
                }
            }
            println(cards.size.toString() + " cards have been saved.")
        } catch (_: Exception) {
            println("Something goes wrong!")
        }
        menu()
    }
    private fun asking() {
        println("How many times to ask?")
        repeat(scan.nextInt()) {
            val term = cards.keys.random()
            println("Print the definition of \"$term\"")
            val answer = readln()
            when {
                answer == cards[term] -> println("Correct!")
                answer in cards.values && answer != cards[term] ->
                    println(
                        "Wrong. The right answer is \"${ cards[term] }\", " +
                        "but your definition is correct for \"${ getKey(answer) }\"."
                    )
                answer !in cards.values ->
                    println("Wrong. The right answer is \"${ cards[term] }\".")
            }
        }
        menu()
    }
    private fun getKey(ans: String): String {
        var res = ""
        for ((key, value) in cards) {
            if (value == ans) res = key
        }
        return res
    }
    private fun exit() {
        println("Bye bye!")
        stop = true
    }
}