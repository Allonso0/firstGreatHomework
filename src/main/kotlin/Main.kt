package org.example

import org.example.di.appModule
import org.example.ui.*
import org.koin.core.context.startKoin
import java.util.*

fun main() {
    // Запускаем Koin.
    startKoin {  modules(appModule) }

    val scanner = Scanner(System.`in`)

    while (true) {
        printMainMenu()

        when(scanner.nextLine()) {
            "1" -> BankAccountUI.start()
            "2" -> CategoryUI.start()
            "3" -> OperationUI.start()
            "4" -> AnalyticsUI.start()
            "5" -> {
                println("=== Модуль \"Учет финансов\" завершил свою работу! ===")
                println("===== Спасибо, что пользуетесь нашим сервисом! =====")
                break
            }
            else -> println("Неверный ввод! Повторите попытку :)")
        }
    }
}

