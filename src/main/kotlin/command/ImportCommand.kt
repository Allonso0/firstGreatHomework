package org.example.command

import org.example.template_method.DataImporter

class ImportCommand<T>(
    private val importer: DataImporter<T>,
    private val filePath: String,
) : Command {
    override fun execute() {
        importer.import(filePath)
        println("Данные успешно импортированы из $filePath")
    }
}