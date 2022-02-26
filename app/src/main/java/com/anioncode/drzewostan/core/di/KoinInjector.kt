package com.anioncode.drzewostan.core.di

import com.anioncode.drzewostan.features.domain.di.treesDataModule
import org.koin.core.module.Module

val koinInjector: List<Module> = listOf(
    databaseModule,
    appModule,
    treesDataModule
)