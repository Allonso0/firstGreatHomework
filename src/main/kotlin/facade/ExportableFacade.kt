package org.example.facade

import org.example.visitor.ExportVisitor

interface ExportableFacade {
    fun export(visitor: ExportVisitor, filePath: String)
}