package org.example.command

class TimeCmdDecorator(private val command: Command) : Command {
    override fun execute() {
        val start = System.currentTimeMillis()
        command.execute()
        val end = System.currentTimeMillis() - start
        println("Время выполнения: $end мс")
    }
}