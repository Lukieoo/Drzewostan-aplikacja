package com.anioncode.drzewostan.features

import com.anioncode.drzewostan.features.domain.model.TreesData

interface TreesDataRepository {
    suspend fun getTreesData(type : Int): List<TreesData>
}