package org.example.domain

import org.example.visitor.ExportVisitor
import java.time.LocalDate
import java.util.UUID

data class Operation (
    val id: UUID,
    val type: OperationType,
    val bank_account_id: UUID,
    val amount: Double,
    val date: LocalDate,
    val description: String?,
    val category_id: UUID
) {
    fun accept(visitor: ExportVisitor): String {
        return visitor.visit(this)
    }
}