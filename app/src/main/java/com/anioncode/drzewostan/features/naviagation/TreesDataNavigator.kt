package com.anioncode.drzewostan.features.naviagation

import com.anioncode.drzewostan.features.presentation.model.TreesDataDisplayable

interface TreesDataNavigator {
    fun openTreesDataScreen(treesDataDisplayable: TreesDataDisplayable)
    fun goBack()
}