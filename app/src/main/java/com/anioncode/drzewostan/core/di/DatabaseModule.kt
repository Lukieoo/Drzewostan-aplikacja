package com.anioncode.drzewostan.core.di

import androidx.room.Room
import com.anioncode.drzewostan.core.database.DatabaseHelper
import com.anioncode.drzewostan.utils.DATABASE_NAME
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            DatabaseHelper::class.java,
            DATABASE_NAME
        ).build()
    }

    single { get<DatabaseHelper>().getTreesDataDao() }
}