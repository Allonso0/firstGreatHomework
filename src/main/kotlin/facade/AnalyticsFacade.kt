package org.example.facade

import org.example.domain.Category
import org.example.domain.OperationType
import org.example.repository.CategoryRepository
import org.example.repository.OperationRepository
import java.time.LocalDate
import java.util.*

class AnalyticsFacade(
    private val operationRepository: OperationRepository,
    private val categoryRepository: CategoryRepository
) {
    fun getTotalByDate(accountID: UUID, type: OperationType, startDate: LocalDate, endDate: LocalDate): Double {
        val operations = operationRepository.findByAccDate(accountID, startDate, endDate)

        return operations.filter { it.type == type }.sumOf { it.amount }
    }

    fun getCategoryReport(accountID: UUID): Map<Category, Double> {
        val operations = operationRepository.findByAcc(accountID)

        return operations.groupBy { it.category_id }
            .mapValues { (_, ops) -> ops.sumOf { it.amount } }
            .mapKeys { (categoryId, _) -> categoryRepository.findById(categoryId) ?: throw IllegalArgumentException("Категория $categoryId не найдена!") }
    }
}