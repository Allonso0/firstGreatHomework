package org.example.di

import org.example.facade.AnalyticsFacade
import org.example.facade.BankAccountFacade
import org.example.facade.CategoryFacade
import org.example.facade.OperationFacade
import org.example.factory.DomainObjectFactory
import org.example.repository.BankAccountRepository
import org.example.repository.CategoryRepository
import org.example.repository.OperationRepository
import org.example.visitor.CsvExportVisitor
import org.example.visitor.JsonExportVisitor
import org.koin.dsl.module


val appModule = module {
    // Репозитории.
    single { BankAccountRepository() }
    single { CategoryRepository() }
    single { OperationRepository() }

    // Фабрика.
    single { DomainObjectFactory() }

    // Фасады.
    single { BankAccountFacade(get(), get()) }
    single { CategoryFacade(get(), get()) }
    single { OperationFacade(get(), get(), get(), get()) }
    single { AnalyticsFacade(get(), get()) }

    // Посетители.
    single { CsvExportVisitor() }
    single { JsonExportVisitor() }
}