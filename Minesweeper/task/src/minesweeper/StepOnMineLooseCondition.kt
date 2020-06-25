package minesweeper

class StepOnMineLooseCondition {

    fun check(fieldPoint: FieldPoint, field: Field): Boolean {
        return fieldPoint.type == FieldPoint.Type.MINE
    }

}