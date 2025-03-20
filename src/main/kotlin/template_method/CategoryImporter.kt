package org.example.template_method

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.example.domain.Category
import org.example.domain.CategoryType
import org.example.facade.CategoryFacade
import java.util.*

class CategoryImporter(private val facade: CategoryFacade) : DataImporter<Category>() {
    override fun parse(data: String): List<Category> {
        return if (data.startsWith("[")) {
            Gson().fromJson(data, object : TypeToken<List<Category>>() {}.type)
        } else {
            data.lines()
                .filter { it.isNotBlank() }
                .map { line ->
                    val parts = line.split(",")
                    Category(
                        id = UUID.fromString(parts[1]),
                        type = CategoryType.valueOf(parts[2]),
                        name = parts[3]
                    )
                }
        }
    }

    override fun validate(data: List<Category>) {
        data.forEach { category ->
            require(category.name.isNotBlank()) { "Название категории не может быть пустым" }
        }
    }

    override fun save(data: List<Category>) {
        data.forEach { category -> facade.saveOrUpdate(category) }
    }
}