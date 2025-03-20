package org.example.domain

import org.example.visitor.ExportVisitor
import java.util.UUID

data class Category(
    val id: UUID,
    val type: CategoryType,
    val name: String
) {
    fun accept(visitor: ExportVisitor): String {
        return visitor.visit(this)
    }
}