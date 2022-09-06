package flashcards

class Flashcards(
    argImp: String,
    argExp: String,
    private val functions: Funs = Funs()
) {
    private val cardList: MutableList<Card> = mutableListOf()
    private var stop = false

    init {
        if (argImp != " ") functions.import(cardList, argImp)
        while (!stop) {
            println("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):".addToLog())
            menu(readln().addToLog())
        }
        if (argExp != " ") functions.export(cardList, argExp)
    }
    private fun menu(input: String) {
        when (input) {
            "add" -> functions.add(cardList)
            "remove" -> functions.remove(cardList)
            "import" -> functions.import(cardList)
            "export" -> functions.export(cardList)
            "ask" -> functions.ask(cardList)
            "log" -> functions.log()
            "hardest card" -> functions.hardestCard(cardList)
            "reset stats" -> functions.reset(cardList)
            "exit" -> stop = functions.exit()
            else -> println("Wrong action!".addToLog())
        }
    }
}

data class Card(
    val term: String,
    val definition: String,
    var mistakes: Int = 0
)
