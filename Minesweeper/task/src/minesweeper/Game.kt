package minesweeper

class Game {
    val field: Field
    val user: User
    val everythingVisibleWinCondition: EverythingVisibleWinCondition
    val markedCorrectlyWinCondition: MarkedCorrectlyWinCondition
    val stepOnMineLooseCondition: StepOnMineLooseCondition
    val defaultFieldInitializer: DefaultFieldInitializer
    val testFieldInitializer: TestFieldInitializer

    init {
        everythingVisibleWinCondition = EverythingVisibleWinCondition()
        markedCorrectlyWinCondition = MarkedCorrectlyWinCondition()
        stepOnMineLooseCondition = StepOnMineLooseCondition()
        field = Field(9, 9)
        user = User()
        defaultFieldInitializer = DefaultFieldInitializer(field, user)
        testFieldInitializer = TestFieldInitializer(field, user)
    }

    fun initializeGameField() {
        defaultFieldInitializer.initializeNumberOfMines()
        defaultFieldInitializer.initMinesOnField()
    }

    fun startGame() {
        readUserAction()
    }

    private fun finishGameSuccess() {
        field.printField()
        println("Congratulations! You found all the mines!")
    }

    private fun finishGameFail() {
        field.makeMinesVisible()
        field.printField()
        println("You stepped on a mine and failed!")
    }

    private fun readUserAction() {
        field.printField()
        user.readInput()
        val fieldPoint = field.getFieldPoint(user.x, user.y)

        if (fieldPoint != null && UserCommand.NULL != user.command) {
            when (user.command) {
                UserCommand.OPEN -> {
                    user.performPrivileges(field, fieldPoint)
                    field.openFieldPoint(fieldPoint)
                    val achieved = everythingVisibleWinCondition.check(fieldPoint, field)
                    if (achieved) {
                        return finishGameSuccess()
                    } else {
                        if (stepOnMineLooseCondition.check(fieldPoint, field)) {
                            return finishGameFail()
                        }
                    }
                }
                UserCommand.MARK -> {
                    field.markFieldAsMine(fieldPoint)
                    val achieved = markedCorrectlyWinCondition.check(fieldPoint, field)
                    if (achieved) {
                        return finishGameSuccess()
                    }
                }
            }
        }
        return readUserAction()
    }

}
