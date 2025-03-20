package org.example.template_method

import java.io.File

abstract class DataImporter<T> {
    fun import(filePath: String) {
        val file = File(filePath)
        require(file.exists()) {
            "Файл не найден: $filePath"
        }

        val data = parse(file.readText())
        validate(data)
        save(data)
    }

    protected abstract fun parse(data: String): List<T>
    protected abstract fun validate(data: List<T>)
    protected abstract fun save(data: List<T>)
}