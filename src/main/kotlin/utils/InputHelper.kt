package org.example.utils

import java.time.LocalDate
import java.time.format.DateTimeParseException
import java.util.*

object InputHelper {
    fun readUUID(scan: Scanner, prompt: String): UUID {
        while (true) {
            print(prompt)
            try {
                return UUID.fromString(scan.nextLine())
            } catch (e: IllegalArgumentException) {
                println("Неверный формат UUID")
            }
        }
    }

    fun readDate(scanner: Scanner, prompt: String): LocalDate {
        while (true) {
            try {
                print(prompt)
                val input = scanner.nextLine()
                return LocalDate.parse(input)
            } catch (e: DateTimeParseException) {
                println("Неверная дата! Необходимо использовать формат гггг-мм-дд\n" +
                        "Например: 2025-12-31.\n" +
                        "Повторите попытку:")
            }
        }
    }
}