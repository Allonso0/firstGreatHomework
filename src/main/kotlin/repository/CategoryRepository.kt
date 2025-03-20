package org.example.repository

import org.example.domain.Category
import java.util.*

class CategoryRepository {
    private val categories = mutableMapOf<UUID, Category>()

    fun save(category: Category) {
        categories[category.id] = category
    }

    fun findById(id: UUID): Category? {
        return categories[id]
    }

    fun findByName(name: String): Category? {
        return categories.values.find { it.name == name }
    }

    fun delete(id: UUID) {
        categories.remove(id)
    }

    fun doesItEvenExist(id: UUID): Boolean {
        return categories.containsKey(id)
    }

    fun findAll(): List<Category> {
        return categories.values.toList()
    }

    fun saveOrUpdate(category: Category) {
        categories[category.id] = category
    }
}