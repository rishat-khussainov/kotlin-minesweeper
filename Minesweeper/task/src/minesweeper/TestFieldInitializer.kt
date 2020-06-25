package minesweeper

class TestFieldInitializer constructor(private val field: Field, private val user: User) {

    private val testPointsEx1: MutableSet<String> = mutableSetOf(
            "10", "06", "25", "51", "54",
            "62", "66", "71", "74", "83")

    private val testPointsEx2: MutableSet<String> = mutableSetOf(
            "01", "06", "08", "26", "57",
            "62", "70", "71")

    private val testPointsEx3: MutableSet<String> = mutableSetOf(
            "26", "31", "48", "56", "81")

    private fun getPointsSet() = testPointsEx2

    private fun getPointFromTestSet(): String {
        val points = getPointsSet().random()
        getPointsSet().remove(points)
        return points
    }

    fun initializeNumberOfMines() {
        field.rebuildField(getPointsSet().size)
    }

    fun initMinesOnField() {
        repeat(field.initialNumberOfMines) {
            field.putMineOnPosition(getPointFromTestSet())
        }
        field.calculateNumberOfMinesAroundEmptyCells()
    }
}