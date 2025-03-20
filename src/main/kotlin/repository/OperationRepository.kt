package org.example.repository

import org.example.domain.Operation
import java.time.LocalDate
import java.util.*

class OperationRepository {
    private val operations = mutableMapOf<UUID, Operation>()

    fun save(operation: Operation) {
        operations[operation.id] = operation
    }

    fun findById(id: UUID): Operation? {
        return operations[id]
    }

    fun findByAccDate(
        accountID: UUID,
        startDate: LocalDate,
        endDate: LocalDate
    ): List<Operation> {
        return operations.values
            .filter { it.bank_account_id == accountID }
            .filter { it.date in startDate..endDate }
    }

    fun findByAcc(
        accountID: UUID
    ): List<Operation> {
        return operations.values
            .filter { it.bank_account_id == accountID }
    }

    fun delete(id: UUID) {
        operations.remove(id)
    }

    fun findAll(): List<Operation> {
        return operations.values.toList()
    }

    fun saveOrUpdate(operation: Operation) {
        operations[operation.id] = operation
    }
}