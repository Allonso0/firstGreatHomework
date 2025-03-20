package org.example.visitor

import org.example.domain.BankAccount
import org.example.domain.Category
import org.example.domain.Operation

interface ExportVisitor {
    fun visit(account: BankAccount): String
    fun visit(category: Category): String
    fun visit(operation: Operation): String
}