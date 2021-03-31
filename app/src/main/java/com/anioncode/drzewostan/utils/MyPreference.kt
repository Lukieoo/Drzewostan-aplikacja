package com.anioncode.drzewostan.utils

import android.content.Context

class MyPreference(context: Context) {

    val PRERFERENCES_NAME = "SHAREDPREFRENCES"
    val PRERFERENCES_ID = "Name"

    val preference = context.getSharedPreferences(PRERFERENCES_NAME, Context.MODE_PRIVATE)

    fun getNameOfUser(): String {
        return preference.getString(PRERFERENCES_ID, "")!!
    }

    fun setNameOfUser(name: String) {
        val editor = preference.edit()
        editor.putString(PRERFERENCES_ID, name)
        editor.apply()
    }
}