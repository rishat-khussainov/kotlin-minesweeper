package minesweeper

class DefaultFieldInitializer constructor(private val field: Field, private val user: User) {

    fun initializeNumberOfMines() {
        println("How many mines do you want on the field?")
        field.rebuildField(user.scanner.nextInt())
    }

    fun initMinesOnField() {
        repeat(field.initialNumberOfMines) {
            field.putRandomMine()
        }
        field.calculateNumberOfMinesAroundEmptyCells()
    }
}