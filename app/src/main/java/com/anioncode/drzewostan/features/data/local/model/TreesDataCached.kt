package com.anioncode.drzewostan.features.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.anioncode.drzewostan.features.domain.model.TreesData

@Entity
data class TreesDataCached(
    @PrimaryKey
    var id: Int,
    var diameter: String?,
    var height: Int,
    var firstClass: Int,
    var secondClass: Int,
    var thirdClass: Int,
    var aClass: Int,
    var bClass: Int,
    var cClass: Int
) {
    constructor(
        treesData: TreesData
    ) : this(
        id = treesData.id,
        diameter = treesData.diameter,
        height = treesData.height,
        firstClass = treesData.firstClass,
        secondClass = treesData.secondClass,
        thirdClass = treesData.thirdClass,
        aClass = treesData.aClass,
        bClass = treesData.bClass,
        cClass = treesData.cClass,
    )

    fun toTreesData() = TreesData(
        id = id,
        diameter = diameter,
        height = height,
        firstClass = firstClass,
        secondClass = secondClass,
        thirdClass = thirdClass,
        aClass = aClass,
        bClass = bClass,
        cClass = cClass,
    )
}
