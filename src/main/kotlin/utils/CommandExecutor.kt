package org.example.utils

import org.example.command.Command

object CommandExecutor {
    fun executeCommand(command: Command) {
        try {
            command.execute()
        } catch (e: Exception) {
            println("Ошибка: ${e.message}")
        }
    }
}