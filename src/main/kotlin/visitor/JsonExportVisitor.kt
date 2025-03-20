package org.example.visitor

import com.google.gson.Gson
import org.example.domain.BankAccount
import org.example.domain.Category
import org.example.domain.Operation

class JsonExportVisitor(private val gson: Gson = Gson()) : ExportVisitor {
    override fun visit(account: BankAccount): String {
        return gson.toJson(account)
    }

    override fun visit(category: Category): String {
        return gson.toJson(category)
    }

    override fun visit(operation: Operation): String {
        return gson.toJson(operation)
    }

    fun exportAll(items: List<Any>): String {
        return gson.toJson(items)
    }
}