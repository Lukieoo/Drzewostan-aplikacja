package com.anioncode.drzewostan.features.presentation.modules.forestStand

import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.VmPolicy
import android.view.View
import com.anioncode.drzewostan.BR
import com.anioncode.drzewostan.R
import com.anioncode.drzewostan.core.base.BaseFragment
import com.anioncode.drzewostan.databinding.FragmentForestStandBinding
import com.anioncode.drzewostan.features.presentation.modules.forestStand.adapters.ViewPagerTreeSpeciesAdapter
import com.anioncode.drzewostan.features.presentation.modules.forestStand.utils.TreeListMock.getTreeKindList
import com.anioncode.drzewostan.features.presentation.modules.forestStand.utils.TreeType
import com.anioncode.drzewostan.features.presentation.modules.forestStand.utils.getStringTreeType
import com.anioncode.drzewostan.features.presentation.modules.forestStand.utils.getTreeType
import com.anioncode.drzewostan.features.presentation.modules.forestStand.view_models.ForestStandViewModel
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel

class ForestStandFragment :
    BaseFragment<ForestStandViewModel, FragmentForestStandBinding>(
        BR.viewModel,
        R.layout.fragment_forest_stand
    ) {
    override val viewModel: ForestStandViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val builder = VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
        builder.detectFileUriExposure()
        val viewPagerAdapter = ViewPagerTreeSpeciesAdapter(requireActivity())
        for (i in 1..TreeType::class.nestedClasses.size) {
            viewPagerAdapter.addFragment(
                TreesPagerFragment(),
                getStringTreeType(getTreeType(i), requireContext(), shorterName = true)
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