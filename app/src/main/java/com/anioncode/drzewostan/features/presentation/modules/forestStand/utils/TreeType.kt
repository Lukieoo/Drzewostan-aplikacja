package com.anioncode.drzewostan.features.presentation.modules.forestStand.utils

import android.content.Context
import com.anioncode.drzewostan.R

sealed class TreeType(val type: Int) {
    object PINE : TreeType(1)
    object OAK : TreeType(2)
    object ALDER : TreeType(3)
    object BIRCH : TreeType(4)
    object REDOAK : TreeType(5)
    object BIRDCHERRY : TreeType(6)
    object BEECH : TreeType(7)
    object HORNBEAM : TreeType(8)
    object FIR : TreeType(9)
    object LARCH : TreeType(10)
    object SPRUCE : TreeType(11)
}

fun getStringTreeType(treeType: TreeType, context: Context, shorterName: Boolean = false): String {
    return when (treeType) {
        is TreeType.ALDER -> context.getString(R.string.alder)
        is TreeType.BEECH -> context.getString(R.string.beech)
        is TreeType.BIRCH -> context.getString(R.string.birch)
        is TreeType.BIRDCHERRY -> context.getString(R.string.birdcherry)
        is TreeType.FIR -> context.getString(R.string.fir)
        is TreeType.HORNBEAM -> context.getString(R.string.hornbeam)
        is TreeType.LARCH -> context.getString(R.string.larch)
        is TreeType.OAK ->
            if (shorterName) context.getString(R.string.oak_sh)
            else context.getString(R.string.oak)
        is TreeType.PINE -> context.getString(R.string.pine)
        is TreeType.REDOAK ->
            if (shorterName) context.getString(R.string.red_oak_sh)
            else context.getString(R.string.red_oak)
        is TreeType.SPRUCE -> context.getString(R.string.spruce)
    }
}

fun getTreeType(value: Int): TreeType {
    return when (value) {
        1 -> TreeType.ALDER
        2 -> TreeType.BEECH
        3 -> TreeType.BIRCH
        4 -> TreeType.BIRDCHERRY
        5 -> TreeType.FIR
        6 -> TreeType.HORNBEAM
        7 -> TreeType.LARCH
        8 -> TreeType.OAK
        9 -> TreeType.PINE
        10 -> TreeType.REDOAK
        11 -> TreeType.SPRUCE
        else -> {
            TreeType.SPRUCE
        }
    }
}