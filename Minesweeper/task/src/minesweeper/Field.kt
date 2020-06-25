package minesweeper

class Field constructor(private val height: Int, private val width: Int) {
    private var array: Array<IntArray> = Array(0) { IntArray(0) }
    var initialNumberOfMines: Int = 0
    private var pointIndexesAroundField: Set<Pair<Int, Int>> = setOf(
            Pair(1, -1), Pair(1, 0), Pair(1, 1),
            Pair(0, -1), Pair(0, 1),
            Pair(-1, -1), Pair(-1, 0), Pair(-1, 1))

    val allPoints: MutableMap<String, FieldPoint> = mutableMapOf()
    private val emptyPoints: MutableMap<String, FieldPoint> = mutableMapOf()
    private val minePoints: MutableMap<String, FieldPoint> = mutableMapOf()


    fun markFieldAsMine(fieldPoint: FieldPoint) = fieldPoint.performOperation(FieldPoint.Operation.MARK)

    fun openFieldPoint(fieldPoint: FieldPoint) {
        when (fieldPoint.type) {
            FieldPoint.Type.MINE -> false
            FieldPoint.Type.EMPTY -> {
                fieldPoint.visible = true
                performOperationForPointRecursively(
                        fieldPoint,
                        FieldPoint.Operation.OPEN,
                        { fp: FieldPoint -> fp.numberOfMinesAround == 0 })
                true
            }
        }
    }

    fun getFieldPoint(x: Int, y: Int): FieldPoint? {
        return allPoints["$y$x"]
    }

    fun removeMine(fieldPoint: FieldPoint) {
        fieldPoint.type = FieldPoint.Type.EMPTY
        removeMinesAround(fieldPoint)
        emptyPoints["${fieldPoint.y}${fieldPoint.x}"] = fieldPoint
    }

    fun removeMinesAround(fieldPoint: FieldPoint) {
        performOperationForPointsAround(fieldPoint, FieldPoint.Operation.REMOVE)
    }

    fun rebuildField(numberOfMines: Int) {
        val numberOfPoints = this.height * width
        this.initialNumberOfMines = if (numberOfMines >= numberOfPoints) numberOfPoints - 1 else numberOfMines
        this.array = Array(height) { IntArray(width) }

        for (y in array.indices) {
            for (x in array[y].indices) {
                val fieldPoint = FieldPoint(x, y)
                allPoints["$y$x"] = fieldPoint
                emptyPoints["$y$x"] = fieldPoint
            }
        }
    }

    fun calculateNumberOfMinesAroundEmptyCells() {
        for (minePoint in minePoints.values) {
            performOperationForPointsAround(minePoint, FieldPoint.Operation.ADD)
        }
    }

    fun makeMinesVisible() {
        for (minePoint in minePoints.values) {
            minePoint.visible = true
        }
    }

    private fun performOperationForPointRecursively(minePoint: FieldPoint,
                                                    operation: FieldPoint.Operation,
                                                    precondition: (p: FieldPoint) -> Boolean) {
        if (precondition.invoke(minePoint)) {
            for (index in pointIndexesAroundField) {
                val fieldPoint = allPoints["${minePoint.y + index.second}${minePoint.x + index.first}"]
                if (fieldPoint != null && !fieldPoint.visible) {
                    val modified = fieldPoint.performOperation(operation)
                    if (modified) {
                        performOperationForPointRecursively(fieldPoint, operation, precondition)
                    }
                }
            }
        }
    }

    fun performOperationForPointsAround(minePoint: FieldPoint, operation: FieldPoint.Operation) {
        for (index in pointIndexesAroundField) {
            val emptyPoint = allPoints["${minePoint.y + index.first}${minePoint.x + index.second}"]
            emptyPoint?.performOperation(operation)
        }
    }

    fun putRandomMine(): FieldPoint {
        val randomPointEntry = emptyPoints.entries.random()
        emptyPoints.remove(randomPointEntry.key)
        randomPointEntry.value.type = FieldPoint.Type.MINE
        minePoints[randomPointEntry.key] = randomPointEntry.value
        return randomPointEntry.value
    }

    fun putMineOnPosition(key: String) {
        val value = emptyPoints[key]!!
        emptyPoints.remove(key)
        value.type = FieldPoint.Type.MINE
        minePoints[key] = value
    }

    fun printField() {
        println(" │123456789│")
        println("—│—————————│")
        for (y in array.indices) {
            print("${y + 1}|")
            for (x in array[y].indices) {
                val fieldPoint = allPoints["$y$x"]!!
                if (fieldPoint.visible) {
                    when {
                        fieldPoint.type == FieldPoint.Type.MINE -> print('X')
                        fieldPoint.numberOfMinesAround > 0 -> print(fieldPoint.numberOfMinesAround)
                        else -> print('/')
                    }
                } else {
                    when {
                        fieldPoint.marked -> print('*')
                        else -> print('.')
                    }
                }
            }
            print("|")
            println()
        }
        println("—│—————————│")
    }

}