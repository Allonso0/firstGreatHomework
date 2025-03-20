package org.example.visitor

import org.example.domain.BankAccount
import org.example.domain.Category
import org.example.domain.Operation

class CsvExportVisitor : ExportVisitor {
    override fun visit(account: BankAccount): String {
        return "BankAccount,${account.id},${account.name},${account.balance}\n"
    }

    override fun visit(category: Category): String {
        return "Category,${category.id},${category.type},${category.name}\n"
    }

    override fun visit(operation: Operation): String {
        return "Operation,${operation.id},${operation.type},${operation.bank_account_id}" +
                ",${operation.category_id},${operation.date},${operation.amount},${operation.description}\n"
    }
}