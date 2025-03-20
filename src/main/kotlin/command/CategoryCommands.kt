package org.example.command

import org.example.domain.CategoryType
import org.example.facade.CategoryFacade
import java.util.UUID

class CreateCategoryCommand(
    private val facade: CategoryFacade,
    private val type: CategoryType,
    private val name: String
) : Command {
    override fun execute() {
        val category = facade.createCategory(type, name)
        println("Категория создана: ${category.name} | ${category.id}")
    }
}

class UpdateCategoryCommand(
    private val facade: CategoryFacade,
    private val categoryID: UUID,
    private val newName: String
) : Command {
    override fun execute() {
        facade.updateCategoryName(categoryID, newName)
        println("Название категории обновлено")
    }
}

class DeleteCategoryCommand(
    private val facade: CategoryFacade,
    private val categoryID: UUID
) : Command {
    override fun execute() {
        facade.deleteCategory(categoryID)
        println("Категория удалена")
    }
}

class GetCategoriesCommand(
    private val facade: CategoryFacade
) : Command {
    override fun execute() {
        val categories = facade.getAllCategories()
        if (categories.isEmpty()) {
            println("Список категорий пуст!")
        } else {
            println("Список категорий:")
            categories.forEach {
                println("ID: ${it.id} | Тип: ${it.type} | Название: ${it.name}")
            }
        }
    }
}