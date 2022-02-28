package com.anioncode.drzewostan.features.domain.model

data class TreesData(
    var id: Int,
    var type: Int,
    var diameter: String?,
    var height: Int,
    var firstClass: Int,
    var secondClass: Int,
    var thirdClass: Int,
    var aClass: Int,
    var bClass: Int,
    var cClass: Int
)
