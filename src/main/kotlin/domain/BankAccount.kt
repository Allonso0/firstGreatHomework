package org.example.domain

import org.example.visitor.ExportVisitor
import java.util.UUID

data class BankAccount(
    val id: UUID,
    val name: String,
    var balance: Double
) {
    fun accept(visitor: ExportVisitor): String {
        return visitor.visit(this)
    }
}