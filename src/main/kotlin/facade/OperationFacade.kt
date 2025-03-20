package org.example.facade

import org.example.domain.Operation
import org.example.domain.OperationType
import org.example.factory.DomainObjectFactory
import org.example.repository.BankAccountRepository
import org.example.repository.CategoryRepository
import org.example.repository.OperationRepository
import org.example.visitor.ExportVisitor
import org.example.visitor.JsonExportVisitor
import java.io.File
import java.time.LocalDate
import java.util.*

class OperationFacade(
    private val repository: OperationRepository,
    private val factory: DomainObjectFactory,
    private val bankAccountRepository: BankAccountRepository,
    private val categoryRepository: CategoryRepository
) : ExportableFacade {
    fun createOperation(
        type: OperationType,
        bankAccountID: UUID,
        amount: Double,
        date: LocalDate,
        description: String?,
        categoryID: UUID
    ): Operation {
        require(bankAccountRepository.doesItEvenExist(bankAccountID)) { "Банковский счет не найден!" }
        require(categoryRepository.doesItEvenExist(categoryID)) { "Категория не найдена!" }

        val operation = factory.createOperation(type, bankAccountID, amount, date, description, categoryID)
        repository.save(operation)
        return operation
    }

    fun saveOrUpdate(operation: Operation) {
        repository.saveOrUpdate(operation)
    }

    fun updateOperation(operationID: UUID, newDescription: String?) {
        val operation = repository.findById(operationID) ?: throw IllegalArgumentException("Операция с ID $operationID не найдена!")
        repository.save(operation.copy(description = newDescription))
    }

    fun deleteOperation(operationID: UUID) {
        repository.delete(operationID)
    }

    fun getOperationsByAccount(operationID: UUID): List<Operation> {
        return repository.findByAcc(operationID)
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