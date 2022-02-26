package com.anioncode.drzewostan.features.presentation.model

import android.os.Parcelable
import com.anioncode.drzewostan.features.domain.model.TreesData
import kotlinx.parcelize.Parcelize

@Parcelize
data class TreesDataDisplayable(
    var id: Int,
    var diameter: String?,
    var height: Int,
    var firstClass: Int,
    var secondClass: Int,
    var thirdClass: Int,
    var aClass: Int,
    var bClass: Int,
    var cClass: Int
) : Parcelable {

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
}
