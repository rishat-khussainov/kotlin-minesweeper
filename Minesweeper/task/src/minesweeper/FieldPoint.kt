package minesweeper

data class FieldPoint(val x: Int, val y: Int) {
    var type: Type = Type.EMPTY
    var numberOfMinesAround: Int = 0
    var visible: Boolean = false
    var marked: Boolean = false

    enum class Type {
        EMPTY, MINE
    }

    enum class Operation {
        ADD, REMOVE, OPEN, MARK
    }

    fun performOperation(operation: Operation): Boolean {
        return when (operation) {
            Operation.ADD -> {
                numberOfMinesAround++
                true
            }
            Operation.REMOVE -> {
                numberOfMinesAround--
                true
            }
            Operation.OPEN -> {
                return if (this.type == Type.EMPTY) {
                    marked = false
                    visible = true
                    this.numberOfMinesAround == 0
                } else {
                    false
                }
            }
            Operation.MARK -> {
                marked = !marked
                true
            }
        }
    }

}