package org.example.command

import org.example.facade.ExportableFacade
import org.example.visitor.ExportVisitor

class ExportCommand(
    private val facade: ExportableFacade,
    private val visitor: ExportVisitor,
    private val filePath: String
) : Command {
    override fun execute() {
        facade.export(visitor, filePath)
        println("Данные экспортированы в $filePath")
    }
}

