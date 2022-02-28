package com.anioncode.drzewostan.features.data.local

import androidx.room.*
import com.anioncode.drzewostan.features.data.local.model.TreesDataCached
import com.anioncode.drzewostan.features.domain.model.TreesData

@Dao
interface TreesDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveTreesData(vararg randomDataCached: TreesDataCached)

    @Query("SELECT * FROM TreesDataCached WHERE id = :type")
    fun getTreesDataList(type: Int): List<TreesDataCached>

    @Update
    fun updateRecord(tree: TreesDataCached): List<TreesDataCached>

    @Query(
        "UPDATE TreesDataCached SET height = 0," +
                "diameter = 0,firstClass = 0,secondClass = 0," +
                "thirdClass = 0,aClass = 0 , bClass = 0 ,cClass = 0"
    )
    fun resetData()
}
