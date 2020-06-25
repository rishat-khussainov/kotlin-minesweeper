package minesweeper

class EverythingVisibleWinCondition {

    fun check(fieldPoint: FieldPoint, field: Field): Boolean {
        var achieved = false
        if (fieldPoint.type == FieldPoint.Type.EMPTY) {
            achieved = !field.allPoints.values
                    .filter { !it.visible && it.type == FieldPoint.Type.EMPTY }
                    .any()
        }
        return achieved
    }

}