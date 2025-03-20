package org.example.facade

import org.example.domain.Category
import org.example.domain.CategoryType
import org.example.factory.DomainObjectFactory
import org.example.repository.CategoryRepository
import org.example.visitor.ExportVisitor
import org.example.visitor.JsonExportVisitor
import java.io.File
import java.util.*

class CategoryFacade(
    private val repository: CategoryRepository,
    private val factory: DomainObjectFactory
) : ExportableFacade {
    fun createCategory(type: CategoryType, name: String): Category {
        require(repository.findByName(name) == null) { "Категория $name уже существует!" }

        val category = factory.createCategory(type, name)
        repository.save(category)
        return category
    }

    fun saveOrUpdate(category: Category) {
        repository.saveOrUpdate(category)
    }

    fun updateCategoryName(id: UUID, newName: String) {
        val category = repository.findById(id) ?: throw IllegalArgumentException("Категория $id не существует")

        require(repository.findByName(newName) == null) { "Категория $newName уже существует!" }

        repository.save(category.copy(name = newName))
    }

    fun deleteCategory(id: UUID) {
        repository.delete(id)
    }

    fun getAllCategories(): List<Category> {
        return repository.findAll()
    }

    override fun export(visitor: ExportVisitor, filePath: String) {
        val data = if (visitor is JsonExportVisitor) {
            visitor.exportAll(repository.findAll())
        } else {
            repository.findAll().joinToString("") { it.accept(visitor) }
        }
        File(filePath).writeText(data)
    }
}