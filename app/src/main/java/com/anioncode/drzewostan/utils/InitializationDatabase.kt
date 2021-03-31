package com.anioncode.drzewostan.utils

import android.content.Context
import com.anioncode.drzewostan.SQLite.DatabaseUniversal

class InitializationDatabase(var context: Context) {

    init {
        for (treeList in TreeListMock.getTreeKindList()){
            createDatabaseForTrees(treeList)
        }
    }
    private fun createDatabaseForTrees(nameOfTreeDb: String) {
        var tmp: Double
        val databaseHelper = DatabaseUniversal(context, nameOfTreeDb)
        var i = 7
        while (i <= 87) {
            if (i < 27) tmp = i + 1.9 else if (i == 27) tmp = i + 3.9 else {
                i += 2
                tmp = i + 3.9
            }
            databaseHelper.insertData("$i-$tmp", 0, 0, 0, 0, 0, 0, 0)
            i += 2
        }
    }
}