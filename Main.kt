package flashcards

import java.util.Scanner
val scan = Scanner(System.`in`)

fun main() {
    println("Input the number of cards:")
    val flashCards = Cards(scan.nextInt())
    flashCards.checkTheAnswer()
}

class Cards(numberOfCards: Int) {
    private val cards = builder(numberOfCards)

    private fun builder(size: Int): Map<String, String> {
        return buildMap {
            for (num in 1..size) {
                var term: String
                var definition: String
                println("Card #$num:")
                do {
                    val key = readln()
                    if (key !in this.keys) {
                        term = key
                        break
                    } else println("The term \"$key\" already exists. Try again:")
                } while (true)
                println("The definition for card #$num:")
                do {
                    val value = readln()
                    if (value !in this.values) {
                        definition = value
                        break
                    } else println("The definition \"$value\" already exists. Try again:")
                } while (true)
                put(term, definition)
            }
        }
    }

    fun checkTheAnswer() {
        for (term in cards.keys) {
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
    }

    private fun getKey(ans: String): String {
        var res = ""
        for ((key, value) in cards) {
            if (value == ans) res = key
        }
        return  res
    }
}