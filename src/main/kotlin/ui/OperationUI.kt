package org.example.ui

import org.example.command.*
import org.example.domain.OperationType
import org.example.facade.OperationFacade
import org.example.template_method.CategoryImporter
import org.example.template_method.OperationImporter
import org.example.utils.CommandExecutor.executeCommand
import org.example.utils.InputHelper.readDate
import org.example.utils.InputHelper.readUUID
import org.example.visitor.CsvExportVisitor
import org.example.visitor.JsonExportVisitor
import org.koin.java.KoinJavaComponent.get
import java.util.*

object OperationUI {
    private val facade: OperationFacade = get(OperationFacade::class.java)
    private val scanner: Scanner = Scanner(System.`in`)

    fun start() {
        while (true) {
            printOpsManagerMenu()

            when(scanner.nextLine()) {
                "1" -> createOperation()
                "2" -> updateOperationDescription()
                "3" -> deleteOperation()
                "4" -> getOperationsList()
                "5" -> manageExport()
                "6" -> importOperations()
                "7" -> return
                else -> println("Неверный ввод! Повторите попытку :)")
            }
        }
    }

    private fun createOperation() {
        print("Тип операции (1 - доход, 2 - расход): ")
        val type = when(scanner.nextLine()) {
            "1" -> OperationType.INCOME
            "2" -> OperationType.EXPENSE
            else -> {
                println("Неверный тип операции!")
                return
            }
        }

        val accountID = readUUID(scanner, "Введите ID счета: ")
        print("Сумма: ")
        val amount = scanner.nextLine().toDoubleOrNull() ?: run {
            println("Некорректная сумма!")
            return
        }

        val date = readDate(scanner, "Дата (гггг-мм-дд): ")

        print("Описание операции (необязательно): ")
        val description = scanner.nextLine().takeIf { it.isNotBlank() }

        val categoryID = readUUID(scanner, "Введите ID категории: ")

        executeCommand(
            TimeCmdDecorator(
                CreateOperationCommand(facade, type, accountID, amount, date, description, categoryID)
            )
        )
    }

    private fun updateOperationDescription() {
        val operationID = readUUID(scanner, "Введите ID операции: ")

        print("Новое описание: ")
        val newDescription = scanner.nextLine()
        executeCommand(
            TimeCmdDecorator(UpdateOperationCommand(facade, operationID, newDescription))
        )
    }

    private fun deleteOperation() {
        val operationID = readUUID(scanner, "Введите ID операции: ")

        executeCommand(
            TimeCmdDecorator(DeleteOperationCommand(facade, operationID))
        )
    }

    private fun getOperationsList() {
        val accountID = readUUID(scanner, "Введите ID счета: ")

        executeCommand(
            GetOperationsCommand(facade, accountID)
        )
    }

    private fun manageExport() {
        printExportMenu()

        when(scanner.nextLine()) {
            "1" -> exportOperation("CSV")
            "2" -> exportOperation("JSON")
            "3" -> return
            else -> println("Неверный ввод! Повторите попытку :)")
        }
    }

    private fun exportOperation(format: String) {
        print("Введите путь для сохранения: ")
        val path = scanner.nextLine()
        val visitor = when(format) {
            "CSV" -> CsvExportVisitor()
            "JSON" -> JsonExportVisitor()
            else -> throw IllegalArgumentException("Неверный формат!")
        }
        executeCommand(
            TimeCmdDecorator(ExportCommand(facade, visitor, path))
        )
    }

    private fun importOperations() {
        print("Введите путь к файлу: ")
        val path = scanner.nextLine()
        val importer = OperationImporter(facade)
        executeCommand(
            TimeCmdDecorator(ImportCommand(importer, path))
        )
    }
}