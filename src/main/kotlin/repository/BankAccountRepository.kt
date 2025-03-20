package org.example.repository

import org.example.domain.BankAccount
import java.util.*

class BankAccountRepository {
    private val accounts = mutableMapOf<UUID, BankAccount>()

    fun save(account: BankAccount) {
        accounts[account.id] = account
    }

    fun findById(id: UUID): BankAccount? {
        return accounts[id]
    }

    fun delete(id: UUID) {
        accounts.remove(id)
    }

    fun doesItEvenExist(id: UUID): Boolean {
        return accounts.containsKey(id)
    }

    fun findAll(): List<BankAccount> {
        return accounts.values.toList()
    }

    fun saveOrUpdate(account: BankAccount) {
        accounts[account.id] = account
    }
}