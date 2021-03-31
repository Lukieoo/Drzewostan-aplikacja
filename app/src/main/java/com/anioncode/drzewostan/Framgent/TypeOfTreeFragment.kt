package com.anioncode.drzewostan.Framgent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.anioncode.drzewostan.Adapters.UniversalAdapter
import com.anioncode.drzewostan.Model.Trees
import com.anioncode.drzewostan.R
import com.anioncode.drzewostan.SQLite.DatabaseUniversal
import java.util.*

private const val ARG_PARAM1 = "param1"

class TypeOfTreeFragment : Fragment() {
    private var paramNameOfTree: String? = null

    var rowPreference: ListView? = null
    var treeTypeList: ArrayList<Trees>? = null
    var universalAdapter: UniversalAdapter? = null
    var databaseHelper: DatabaseUniversal? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            paramNameOfTree = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.universal_layout, container, false)

        rowPreference = view.findViewById<View>(R.id.listView) as ListView
        databaseHelper = DatabaseUniversal(view.context, paramNameOfTree)
        treeTypeList = ArrayList()
        treeTypeList = databaseHelper!!.allData
        universalAdapter = UniversalAdapter(view.context, treeTypeList, paramNameOfTree)
        rowPreference!!.setAdapter(universalAdapter)
        universalAdapter!!.notifyDataSetChanged()

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
                TypeOfTreeFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                    }
                }
    }
}