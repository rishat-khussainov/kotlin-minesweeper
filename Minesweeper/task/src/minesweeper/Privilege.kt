package minesweeper

class Privilege {

    fun perform(field: Field, fieldPoint: FieldPoint): Boolean {
        return if (FieldPoint.Type.MINE == fieldPoint.type) {
            val newMine = field.putRandomMine()
            field.removeMine(fieldPoint)
            field.performOperationForPointsAround(newMine, FieldPoint.Operation.ADD)
            true
        } else {
            true
        }
    }
}