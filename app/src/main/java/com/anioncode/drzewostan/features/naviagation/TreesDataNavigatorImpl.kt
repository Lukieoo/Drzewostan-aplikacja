package com.anioncode.drzewostan.features.naviagation

import com.anioncode.drzewostan.R
import com.anioncode.drzewostan.core.navigation.FragmentNavigator
import com.anioncode.drzewostan.features.presentation.model.TreesDataDisplayable

class TreesDataNavigatorImpl(private val fragmentNavigator: FragmentNavigator) :
    TreesDataNavigator {

    override fun openTreesDataScreen(treesDataDisplayable: TreesDataDisplayable) {
//        fragmentNavigator.navigateTo(
//            R.id.action_mainFragment_to_treesDataDetailsFragment,
//            TreesDataDetailsFragment.DETAILS_KEY to treesDataDisplayable
//        )
    }

    override fun goBack() {
        fragmentNavigator.goBack()
    }
}