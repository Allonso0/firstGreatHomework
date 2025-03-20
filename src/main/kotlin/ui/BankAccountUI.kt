package org.example.ui

import org.example.command.*
import org.example.facade.BankAccountFacade
import org.example.template_method.BankAccountImporter
import org.example.utils.CommandExecutor.executeCommand
import org.example.utils.InputHelper.readUUID
import org.example.visitor.CsvExportVisitor
import org.example.visitor.JsonExportVisitor
import org.koin.java.KoinJavaComponent.get
import java.util.*

object BankAccountUI {
    private val facade: BankAccountFacade = get(BankAccountFacade::class.java)
    private val scanner = Scanner(System.`in`)

    fun start() {
        while (true) {
            printBAManagerMenu()

            when(scanner.nextLine()) {
                "1" -> createBankAccount()
                "2" -> updateBankAccount()
                "3" -> deleteBankAccount()
                "4" -> executeCommand(GetBankAccountsCommand(facade))
                "5" -> manageExport()
                "6" -> importAccounts()
                "7" -> return
                else -> println("Неверный ввод! Повторите попытку :)")
            }
        }
    }

    private fun createBankAccount() {
        print("Введите название счета: ")
        val name = scanner.nextLine().takeIf { it.isNotBlank() } ?: run {
            println("Название счета не может быть пустым!")
            return
        }

        print("Введите начальный баланс: ")
        val balance = scanner.nextLine().toDoubleOrNull() ?: run {
            println("Некорректная сумма")
            return
        }

        executeCommand(
            TimeCmdDecorator(CreateBankAccountCommand(facade, name, balance))
        )
    }

    private fun updateBankAccount() {
        val accountID = readUUID(scanner, "Введите ID счета: ")
        print("Введите новый баланс: ")
        val balance = scanner.nextLine().toDoubleOrNull() ?: run {
            println("Некорректное значение баланса")
            return
        }

        executeCommand(
            TimeCmdDecorator(UpdateBankAccountCommand(facade, accountID, balance))
        )
    }

    private fun deleteBankAccount() {
        val account = readUUID(scanner, "Введите ID счета: ")
        executeCommand(
            TimeCmdDecorator(DeleteBankAccountCommand(facade, account))
        )
    }

    private fun manageExport() {
        printExportMenu()

        when(scanner.nextLine()) {
            "1" -> exportAccounts("CSV")
            "2" -> exportAccounts("JSON")
            "3" -> return
            else -> println("Неверный ввод! Повторите попытку :)")
        }
    }

    private fun exportAccounts(format: String) {
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

    private fun importAccounts() {
        print("Введите путь к файлу: ")
        val path = scanner.nextLine()
        val importer = BankAccountImporter(facade)
        executeCommand(
            TimeCmdDecorator(ImportCommand(importer, path))
        )
    }
}