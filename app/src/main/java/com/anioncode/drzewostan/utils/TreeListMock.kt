package com.anioncode.drzewostan.utils

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
     fun translateTree(nameOfTree: String): String {
        return when (nameOfTree) {
            "PINE" -> "Sosna"
            "OAK" -> "Dąb Sz."
            "ALDER" -> "Olszyna"
            "BIRCH" -> "Brzoza"
            "OAK_RED" -> "Dąb Czer."
            "BIRDCHERRY" -> "Czeremch"
            "BEECH" -> "Buk"
            "HORNBEAM" -> "Grab"
            "FIR" -> "Jodła"
            "LARCH" -> "Modrzew"
            "SPRUCE" -> "Świerk"
            else -> ""
        }
    }
    fun translateTreeFullName(nameOfTree: String): String {
        return when (nameOfTree) {
            "PINE" -> "Sosna"
            "OAK" -> "Dąb Szypułkowy"
            "ALDER" -> "Olszyna"
            "BIRCH" -> "Brzoza"
            "OAK_RED" -> "Dąb Czerrwony"
            "BIRDCHERRY" -> "Czeremch"
            "BEECH" -> "Buk"
            "HORNBEAM" -> "Grab"
            "FIR" -> "Jodła"
            "LARCH" -> "Modrzew"
            "SPRUCE" -> "Świerk"
            else -> ""
        }
    }
}