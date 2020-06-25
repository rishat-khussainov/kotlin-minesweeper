package minesweeper

import java.util.*

class User {
    var x: Int = 0
    var y: Int = 0
    var command: UserCommand = UserCommand.OPEN
    val scanner = Scanner(System.`in`)

    private var privileges: MutableSet<Privilege> = mutableSetOf(Privilege())

    fun readInput() {
        println("Set/unset mines marks or claim a cell as free:")
        x = scanner.nextInt() - 1
        y = scanner.nextInt() - 1
        command = UserCommand.getByValue(scanner.next())
    }

    fun performPrivileges(field: Field, fieldPoint: FieldPoint) {
        val usedPrivileges: MutableSet<Privilege> = mutableSetOf()
        for (privilege in privileges) {
            val performed = privilege.perform(field, fieldPoint)
            if (performed) usedPrivileges.add(privilege)
        }
        privileges.removeAll(usedPrivileges)
    }
}