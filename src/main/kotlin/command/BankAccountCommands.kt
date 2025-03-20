package org.example.command

import org.example.facade.BankAccountFacade
import java.util.UUID

class CreateBankAccountCommand(
    private val facade: BankAccountFacade,
    private val name: String,
    private val startBalance: Double
) : Command {
    override fun execute() {
        val account = facade.createAccount(name, startBalance)
        println("Счет создан: ${account.id}")
    }
}

class UpdateBankAccountCommand(
    private val facade: BankAccountFacade,
    private val accountID: UUID,
    private val newBalance: Double
) : Command {
    override fun execute() {
        facade.updateBalance(accountID, newBalance)
        println("Баланс счета обновлен")
    }
}

class DeleteBankAccountCommand(
    private val facade: BankAccountFacade,
    private val accountID: UUID
) : Command {
    override fun execute() {
        facade.deleteAccount(accountID)
        println("Счет удален")
    }
}

class GetBankAccountsCommand(
    private val facade: BankAccountFacade
) : Command {
    override fun execute() {
        val accounts = facade.getAllAccounts()
        if (accounts.isEmpty()) {
            println("Список счетов пуст")
        } else {
            println("Список счетов:")
            accounts.forEach {
                println("ID: ${it.id} | Название: ${it.name} | Баланс: ${ it.balance }")
            }
        }
    }
}