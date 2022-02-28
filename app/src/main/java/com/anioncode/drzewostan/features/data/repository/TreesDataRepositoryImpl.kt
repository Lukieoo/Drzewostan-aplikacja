package com.anioncode.drzewostan.features.data.repository

import com.anioncode.drzewostan.features.TreesDataRepository
import com.anioncode.drzewostan.features.data.local.TreesDataDao
import com.anioncode.drzewostan.features.domain.model.TreesData

class TreesDataRepositoryImpl(
    private val treesDataDao: TreesDataDao,
) : TreesDataRepository {
    override suspend fun getTreesData(type : Int): List<TreesData> {
        return getTreesDataLocal(type)
    }

    private fun getTreesDataLocal(type : Int): List<TreesData> {
        return treesDataDao.getTreesDataList(type)
            .map { it.toTreesData() }
    }
}