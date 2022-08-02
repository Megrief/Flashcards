package flashcards


fun main() {
    println(Text().textArr[0])
    run { Cards(readln().toInt()) }

}

class Text {
    val textArr = arrayOf(
        "Input the number of cards: ",
        "Card #",
        "The definition for card #",
        "Print the definition of",
        "Correct!",
        "Wrong. The right answer is"
    )
}

class Cards(private val numberOfCards: Int) {
    private val terms = MutableList(numberOfCards) { " " }
    private val definitions = MutableList(numberOfCards) { " " }

    init {
        creatingCard()
        for (i in 0 until numberOfCards) {
            println( if (checkTheAnswer(answer(i), i)) Text().textArr[4] else """
                ${ Text().textArr[5] } "${ definitions[i] }"
                 """.trimIndent()
            )
        }
    }
    private fun checkTheAnswer(answer: String, ind: Int) = answer == definitions[ind]

    private fun creatingCard() {
        for (i in 0 until numberOfCards) {
            println(Text().textArr[1] + (i + 1).toString())
            terms[i] = readln()
            println(Text().textArr[2] + (i + 1).toString())
            definitions[i] = readln()
        }
    }

    private fun answer(ind: Int): String {
        println("""
            ${ Text().textArr[3] } "${ terms[ind] }"
        """.trimIndent()
        )
        return readln()
    }
}