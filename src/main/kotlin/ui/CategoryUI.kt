package org.example.ui

import org.example.command.*
import org.example.domain.CategoryType
import org.example.facade.CategoryFacade
import org.example.template_method.BankAccountImporter
import org.example.template_method.CategoryImporter
import org.example.utils.CommandExecutor.executeCommand
import org.example.utils.InputHelper.readUUID
import org.example.visitor.CsvExportVisitor
import org.example.visitor.JsonExportVisitor
import org.koin.java.KoinJavaComponent.get
import java.util.*

object CategoryUI {
    private val facade: CategoryFacade = get(CategoryFacade::class.java)
    private val scanner: Scanner = Scanner(System.`in`)

    fun start() {
        while(true) {
            printCtgManagerMenu()

            when(scanner.nextLine()) {
                "1" -> createCategory()
                "2" -> updateCategory()
                "3" -> deleteCategory()
                "4" -> executeCommand(GetCategoriesCommand(facade))
                "5" -> manageExport()
                "6" -> importCategories()
                "7" -> return
                else -> println("Неверный ввод! Повторите попытку :)")
            }
        }
    }

    private fun createCategory() {
        print("Введите тип категории (1 - доход, 2 - расход): ")
        val type = when (scanner.nextLine()) {
            "1" -> CategoryType.INCOME
            "2" -> CategoryType.EXPENSE
            else -> {
                println("Неверный тип категории!")
                return
            }
        }

        print("Введите название категории: ")
        val name = scanner.nextLine().takeIf { it.isNotBlank() } ?: run {
            println("Название категории не может быть пустым!")
            return
        }

        executeCommand(
            TimeCmdDecorator(CreateCategoryCommand(facade, type, name))
        )
    }

    private fun updateCategory() {
        val categoryID = readUUID(scanner, "Введите ID категории: ")

        print("Введите новое название категории: ")
        val newName = scanner.nextLine().takeIf { it.isNotBlank() } ?: run {
            println("Название категории не может быть пустым!")
            return
        }

        executeCommand(
            TimeCmdDecorator(UpdateCategoryCommand(facade, categoryID, newName))
        )
    }

    private fun deleteCategory() {
        val categoryID = readUUID(scanner, "Введите ID категории: ")

        executeCommand(
            TimeCmdDecorator(DeleteCategoryCommand(facade, categoryID)))
    }

    private fun manageExport() {
        printExportMenu()

        when(scanner.nextLine()) {
            "1" -> exportCategory("CSV")
            "2" -> exportCategory("JSON")
            "3" -> return
            else -> println("Неверный ввод! Повторите попытку :)")
        }
    }

    private fun exportCategory(format: String) {
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

    private fun importCategories() {
        print("Введите путь к файлу: ")
        val path = scanner.nextLine()
        val importer = CategoryImporter(facade)
        executeCommand(
            TimeCmdDecorator(ImportCommand(importer, path))
        )
    }
}