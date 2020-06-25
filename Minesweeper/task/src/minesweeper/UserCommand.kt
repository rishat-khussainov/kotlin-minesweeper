package minesweeper

enum class UserCommand(val value: String) {
    MARK("mine"), OPEN("free"), NULL("null");

    companion object {
        fun getByValue(value: String): UserCommand {
            for (enum in values()) {
                if (enum.value == value) {
                    return enum;
                }
            }
            return NULL
        }
    }
}