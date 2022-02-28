package com.anioncode.drzewostan.features.presentation.modules.forestStand.utils

import android.content.Context
import com.anioncode.drzewostan.R
import java.util.*

object TreeListMock {
    fun getTreeKindList(): MutableList<String> {
        val treeList = ArrayList<String>()
        treeList.add("PINE")
        treeList.add("OAK")
        treeList.add("ALDER")
        treeList.add("BIRCH")
        treeList.add("OAK_RED")
        treeList.add("BIRDCHERRY")
        treeList.add("BEECH")
        treeList.add("HORNBEAM")
        treeList.add("FIR")
        treeList.add("LARCH")
        treeList.add("SPRUCE")
        return treeList
    }
}