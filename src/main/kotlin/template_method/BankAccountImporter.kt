package org.example.template_method

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.example.domain.BankAccount
import org.example.facade.BankAccountFacade
import java.util.*

class BankAccountImporter(private val facade: BankAccountFacade) : DataImporter<BankAccount>() {
    override fun parse(data: String): List<BankAccount> {
        return if (data.startsWith("[")) {
            Gson().fromJson(data, object : TypeToken<List<BankAccount>>() {}.type)
        } else {
            data.lines()
                .filter { it.isNotBlank() }
                .map { line ->
                    val parts = line.split(",")
                    BankAccount(
                        id = UUID.fromString(parts[1]),
                        name = parts[2],
                        balance = parts[3].toDouble()
                    )
                }
        }
    }

    override fun validate(data: List<BankAccount>) {
        data.forEach { account ->
            require(account.name.isNotBlank()) { "Название аккаунта не может быть пустым!" }
            require(account.balance >= 0) { "Баланс не может быть отрицательным!" }
        }
    }

    override fun save(data: List<BankAccount>) {
        data.forEach { bankAccount -> facade.saveOrUpdate(bankAccount) }
    }
}