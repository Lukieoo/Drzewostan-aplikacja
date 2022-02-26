package com.anioncode.drzewostan.features.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.anioncode.drzewostan.features.data.local.model.TreesDataCached

@Dao
interface TreesDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveTreesData(vararg randomDataCached: TreesDataCached)

    @Query("SELECT * FROM TreesDataCached")
    fun getTreesDataList(): List<TreesDataCached>
}
