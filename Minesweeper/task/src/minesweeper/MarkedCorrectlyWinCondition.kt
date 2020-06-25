package minesweeper

class MarkedCorrectlyWinCondition {

    private var markedMines: Int = 0
    private var markedEmpties: Int = 0

    fun check(fieldPoint: FieldPoint, field: Field): Boolean {
        when (fieldPoint.type) {
            FieldPoint.Type.MINE -> if (fieldPoint.marked) markedMines++ else markedMines--
            FieldPoint.Type.EMPTY -> if (fieldPoint.marked) markedEmpties++ else markedEmpties--
        }
        return field.initialNumberOfMines == markedMines && markedEmpties == 0
    }

}