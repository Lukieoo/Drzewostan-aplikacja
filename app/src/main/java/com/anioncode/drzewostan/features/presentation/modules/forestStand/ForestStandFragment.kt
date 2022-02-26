package com.anioncode.drzewostan.features.presentation.modules.forestStand

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.VmPolicy
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.viewpager.widget.ViewPager
import com.anioncode.drzewostan.BR
import com.anioncode.drzewostan.R
import com.anioncode.drzewostan.core.base.BaseFragment
import com.anioncode.drzewostan.databinding.FragmentForestStandBinding
import com.anioncode.drzewostan.features.presentation.modules.forestStand.TreeListMock.getTreeKindList
import com.anioncode.drzewostan.features.presentation.modules.forestStand.TreeListMock.translateTree
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.reflect.KParameter

class ForestStandFragment :
    BaseFragment<ForestStandViewModel, FragmentForestStandBinding>(
        BR.viewModel,
        R.layout.fragment_forest_stand
    ) {
    override val viewModel: ForestStandViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val builder = VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
        builder.detectFileUriExposure()


        val viewPagerAdapter = ViewPagerTreeSpeciesAdapter(requireActivity())
        for (name in getTreeKindList()) {
            println(name)
            viewPagerAdapter.addFragment(
                BaseFragment(),
                translateTree(name)
            )
        }
        binding?.viewPager!!.let {
            it.adapter = viewPagerAdapter
            TabLayoutMediator(binding?.tabLayout!!, it) { tab, position ->
                tab.text = getTreeKindList()[position]
            }.attach()
        }

    }
}