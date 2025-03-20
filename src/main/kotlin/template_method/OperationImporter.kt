package org.example.template_method

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.example.domain.Operation
import org.example.domain.OperationType
import org.example.facade.OperationFacade
import java.time.LocalDate
import java.util.*

class OperationImporter(private val facade: OperationFacade) : DataImporter<Operation>() {
    override fun parse(data: String): List<Operation> {
        return if (data.startsWith("[")) {
            Gson().fromJson(data, object : TypeToken<List<Operation>>() {}.type)
        } else {
            data.lines()
                .filter { it.isNotBlank() }
                .map { line ->
                    val parts = line.split(",")
                    Operation(
                        id = UUID.fromString(parts[1]),
                        type = OperationType.valueOf(parts[2]),
                        bank_account_id = UUID.fromString(parts[3]),
                        category_id = UUID.fromString(parts[4]),
                        date = LocalDate.parse(parts[5]),
                        amount = parts[6].toDouble(),
                        description = parts[7].takeIf { it.isNotBlank() }
                    )
                }
        }
    }

    override fun validate(data: List<Operation>) {
        data.forEach { operation ->
            require(operation.amount > 0) { "Сумма операции должна быть положительной!" }
            require(!operation.date.isAfter(LocalDate.now())) { "Дата операции не может быть в будущем!" }
        }
    }

    override fun save(data: List<Operation>) {
        data.forEach { operation ->
            facade.saveOrUpdate(operation)
        }
    }
}