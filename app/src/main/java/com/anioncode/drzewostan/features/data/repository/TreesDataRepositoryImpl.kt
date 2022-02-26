package com.anioncode.drzewostan.features.data.repository

import com.anioncode.drzewostan.features.TreesDataRepository
import com.anioncode.drzewostan.features.data.local.TreesDataDao
import com.anioncode.drzewostan.features.domain.model.TreesData

class TreesDataRepositoryImpl(
    private val treesDataDao: TreesDataDao,
) : TreesDataRepository {
    override suspend fun getTreesData(): List<TreesData> {
        return getTreesDataLocal()
    }

    private fun getTreesDataLocal(): List<TreesData> {
        return treesDataDao.getTreesDataList()
            .map { it.toTreesData() }
    }
}