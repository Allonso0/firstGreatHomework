package org.example.factory

import org.example.domain.*
import java.time.LocalDate
import java.util.*

class DomainObjectFactory {

    fun createBankAccount(name: String, startBalance: Double): BankAccount {
        require(name.isNotEmpty()) { "Имя не должно быть пустым!" }
        require(startBalance >= 0) { "Начальный баланс не должен быть отрицательным!" }

        return BankAccount(
            id = UUID.randomUUID(),
            name = name,
            balance = startBalance
        )
    }

    fun createCategory(type: CategoryType, name: String): Category {
        require(name.isNotEmpty()) { "Имя не должно быть пустым!" }

        return Category(
            id = UUID.randomUUID(),
            type = type,
            name = name
        )
    }

    fun createOperation(
        type: OperationType,
        bankAccountID: UUID,
        amount: Double,
        date: LocalDate,
        description: String?,
        categoryID: UUID
    ): Operation {
        require(amount > 0) { "Сумма операции должна быть положительной!" }
        require(!date.isAfter(LocalDate.now())) { "Дата операции не может быть в будущем!" }

        return Operation(
            id = UUID.randomUUID(),
            type = type,
            bank_account_id = bankAccountID,
            amount = amount,
            date = date,
            description = description,
            category_id = categoryID
        )
    }
}