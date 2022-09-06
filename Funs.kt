package flashcards

import java.io.File
import java.io.FileNotFoundException


class Funs {

    fun add(list: MutableList<Card>) {
        println("The card:".addToLog())
        val term = readln().addToLog()
        if (list.any { it.term == term }) return println("The card \"$term\" already exists.".addToLog())
        println("The definition of the card:".addToLog())
        val definition = readln().addToLog()
        if (list.any { it.definition == definition }) return println("The definition \"$definition\" already exists.".addToLog())
        list.add(Card(term, definition))
        println("The pair (\"$term\":\"$definition\") has been added.".addToLog())
    }

    fun remove(list: MutableList<Card>) {
        println("Which card?".addToLog())
        val input = readln().addToLog()
        list.forEachIndexed { index, card ->
            if (card.term == input) {
                list.removeAt(index)
                return println("The card has been removed.".addToLog())
            }
        }
        println("Can't remove \"$input\": there is no such card.".addToLog())
    }
    private fun fileName(): String {
        println("File name:".addToLog())
        return readln().addToLog()
    }

    fun import(list: MutableList<Card>, fName: String = fileName()) {
        try {
            val imported = File(fName).readLines().map { it.split(':').toCard() }.toMutableList()
            val size = imported.size
            for (ind in imported.indices) {
                list.forEachIndexed { index, card ->
                    if (imported[ind].term == card.term || imported[ind].definition == card.definition) {
                        imported[ind].mistakes += card.mistakes
                        list[index] = imported[ind]
                        imported.removeAt(ind)
                    }
                }
            }
            list += imported
            println("$size cards have been loaded.".addToLog())
        } catch (_: FileNotFoundException) {
            return println("File not found.".addToLog())
        }
    }
    private fun List<String>.toCard(): Card {
        return when {
            this.isEmpty() -> throw Exception("List is empty!")
            this.size == 2 -> Card(this[0], this[1], 0)
            this.size == 3 -> Card(this[0], this[1], this[2].toInt())
            else -> throw Exception("Wrong text format!")
        }
    }

    fun export(list: MutableList<Card>, fName: String = fileName()) {
        try {
            val savedCards = File(fName)
            val lines = buildList {
                list.forEach {
                    this.add("${ it.term }:${ it.definition }:${ it.mistakes }")
                }
            }
            for (ind in lines.indices) {
                if (ind == 0) {
                    savedCards.writeText("""
                    ${ lines[ind] }
                    
                    """.trimIndent()
                    )
                } else {
                    savedCards.appendText("""
                    ${ lines[ind] }
                    
                    """.trimIndent()
                    )
                }
            }
            println((list.size.toString() + " cards have been saved.").addToLog())
        } catch (_: Exception) {
            println("Something goes wrong!".addToLog())
        }
    }

    fun ask(list: MutableList<Card>) {
        println("How many times to ask?".addToLog())
        val input: Int = try {
            readln().addToLog().toInt()
        } catch (e: NumberFormatException) {
            println("Input a number!".addToLog())
            readln().addToLog().toInt()
        }
        repeat(input) {
            val index = random.nextInt(0, list.size)
            val term = list[index].term
            val def = list[index].definition
            println("Print the definition of \"$term\"".addToLog())
            val answer = readln().addToLog()
            when {
                answer == def -> println("Correct!".addToLog())
                check(list, answer).isNotEmpty() -> {
                    println(
                        ("Wrong. The right answer is \"${def}\", " +
                        "but your definition is correct for \"${ check(list, answer).first().term }\".").addToLog())
                    list[index].mistakes++
                }
                check(list, answer).isEmpty() -> {
                    println("Wrong. The right answer is \"${def}\".".addToLog())
                    list[index].mistakes++
                }
            }
        }
    }
    private fun check(list: MutableList<Card>, def: String): List<Card> {
        return list.filter { it.definition == def }
    }

    fun log() {
        println("File name:".addToLog())
        val logFile = File(readln().addToLog())
        println("The log has been saved.".addToLog())
        for (ind in logList.indices) {
            if (ind == 0) {
                logFile.writeText("""
                    ${ logList[ind] }
                    
                """.trimIndent())
            } else {
                logFile.appendText("""
                    ${ logList[ind] }
                    
                """.trimIndent())
            }
        }
    }

    fun hardestCard(list: MutableList<Card>) {
        val filtered = list.filter { it.mistakes == list.maxOf { card -> card.mistakes } }
        if (filtered.any { it.mistakes > 0}) {
            if (filtered.size == 1) {
                println(
                    ("The hardest card is \"${filtered.first().term}\". " +
                     "You have ${filtered.first().mistakes} errors answering it.").addToLog())
            } else {
                val li = buildList {
                    this.add("The hardest cards are")
                    for (ind in filtered.indices) {
                        if (ind == filtered.lastIndex) this.add("\"${ filtered[ind].term }\".")
                        else this.add("\"${filtered[ind].term}\",")
                    }
                    this.add("You have ${filtered.first().mistakes} errors answering them.")
                }
                println(li.joinToString(" ").addToLog())
            }
        } else println("There are no cards with errors.".addToLog())
    }

    fun reset(list: MutableList<Card>) {
        list.forEach {
            it.mistakes = 0
        }
        println("Card statistics have been reset.".addToLog())
    }

    fun exit(): Boolean {
        println("Bye bye!".addToLog())
        return true
    }
}