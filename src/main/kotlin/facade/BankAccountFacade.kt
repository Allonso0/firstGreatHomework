package org.example.facade

import org.example.domain.BankAccount
import org.example.factory.DomainObjectFactory
import org.example.repository.BankAccountRepository
import org.example.visitor.ExportVisitor
import org.example.visitor.JsonExportVisitor
import java.io.File
import java.util.*

class BankAccountFacade(
    private val repository: BankAccountRepository,
    private val factory: DomainObjectFactory
) : ExportableFacade {
    fun createAccount(name: String, startBalance: Double): BankAccount {
        val account = factory.createBankAccount(name, startBalance)
        repository.save(account)
        return account
    }

    fun saveOrUpdate(account: BankAccount) {
        repository.saveOrUpdate(account)
    }

    fun updateBalance(accountID: UUID, newBalance: Double) {
        require(newBalance >= 0) { "Баланс не должен быть отрицательным!" }

        val account = repository.findById(accountID) ?: throw  IllegalArgumentException("Аккаунт $accountID не был найден!")
        account.balance = newBalance
        repository.save(account)
    }

    fun deleteAccount(accountID: UUID) {
        repository.delete(accountID)
    }

    fun getAllAccounts(): List<BankAccount> {
        return repository.findAll()
    }

    override fun export(visitor: ExportVisitor, filePath: String) {
        val data = if (visitor is JsonExportVisitor) {
            visitor.exportAll(repository.findAll())
        } else {
            repository.findAll().joinToString("") { it.accept(visitor) }
        }
        File(filePath).writeText(data)
    }
}