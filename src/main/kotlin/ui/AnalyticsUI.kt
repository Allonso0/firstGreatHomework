package org.example.ui

import org.example.domain.OperationType
import org.example.facade.AnalyticsFacade
import org.example.utils.InputHelper.readDate
import org.example.utils.InputHelper.readUUID
import org.koin.java.KoinJavaComponent.get
import java.util.*

object AnalyticsUI {
    private val analyticsFacade: AnalyticsFacade = get(AnalyticsFacade::class.java)
    private val scanner = Scanner(System.`in`)

    fun start() {
        while (true) {
            printAnalyticsManagerMenu()

            when(scanner.nextLine()) {
                "1" -> calculateBalanceDiff(scanner, analyticsFacade)
                "2" -> getCategoryReport(scanner, analyticsFacade)
                "3" -> return
                else -> println("Неверный ввод! Повторите попытку :)")
            }
        }
    }

    private fun calculateBalanceDiff(scanner: Scanner, facade: AnalyticsFacade) {
        val accountID = readUUID(scanner, "Введите ID счета: ")
        val startDate = readDate(scanner, "Введите начальную дату: ")
        val endDate = readDate(scanner, "Введите конечную дату: ")

        val income = facade.getTotalByDate(accountID, OperationType.INCOME, startDate, endDate)
        val expense = facade.getTotalByDate(accountID, OperationType.EXPENSE, startDate, endDate)
        println("Аналитика за временной промежуток $startDate - $endDate \n" +
                "Доходы: $income\n" +
                "Расходы: $expense\n" +
                "Разница: ${income - expense}")
    }

    private fun getCategoryReport(scanner: Scanner, facade: AnalyticsFacade) {
        val accountID = readUUID(scanner, "Введите ID счета: ")

        val report = facade.getCategoryReport(accountID)

        println("Отчет по категориям:")
        report.forEach { (category, amount) ->
            println("${category.name} (${category.type}): $amount")
        }
    }
}