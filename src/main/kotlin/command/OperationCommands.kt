package org.example.command

import org.example.domain.OperationType
import org.example.facade.OperationFacade
import java.time.LocalDate
import java.util.UUID

class CreateOperationCommand(
    private val facade: OperationFacade,
    private val type: OperationType,
    private val bankAccountID: UUID,
    private val amount: Double,
    private val date: LocalDate,
    private val description: String?,
    private val categoryID: UUID
) : Command {
    override fun execute() {
        val operation = facade.createOperation(
            type = type,
            bankAccountID = bankAccountID,
            amount = amount,
            date = date,
            description = description,
            categoryID = categoryID
        )
        println("Операция создана: ${operation.id}")
    }
}

class UpdateOperationCommand(
    private val facade: OperationFacade,
    private val operationID: UUID,
    private val newDescription: String?
) : Command {
    override fun execute() {
        facade.updateOperation(operationID, newDescription)
        println("Описание операции обновлено")
    }
}

class DeleteOperationCommand(
    private val facade: OperationFacade,
    private val operationID: UUID
) : Command {
    override fun execute() {
        facade.deleteOperation(operationID)
        println("Операция успешно удалена")
    }
}

class GetOperationsCommand(
    private val facade: OperationFacade,
    private val accountId: UUID
) : Command {
    override fun execute() {
        val operations = facade.getOperationsByAccount(accountId)
        if (operations.isEmpty()) {
            println("Список операций пуст!")
        } else {
            println("Список операций:")
            operations.forEach {
                println("ID: ${it.id} | Дата: ${it.date} | Тип: ${it.type} | Сумма: ${it.amount}")
            }
        }
    }
}