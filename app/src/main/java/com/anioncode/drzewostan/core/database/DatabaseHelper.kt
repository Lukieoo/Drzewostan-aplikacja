package com.anioncode.drzewostan.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.anioncode.drzewostan.features.data.local.TreesDataDao
import com.anioncode.drzewostan.features.data.local.model.TreesDataCached

@Database(
    entities = [TreesDataCached::class],
    version = 1, exportSchema = false
)

@TypeConverters(ListConverter::class)
abstract class DatabaseHelper : RoomDatabase() {
    abstract fun getTreesDataDao(): TreesDataDao
}