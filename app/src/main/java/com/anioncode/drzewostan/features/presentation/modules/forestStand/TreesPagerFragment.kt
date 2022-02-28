package com.anioncode.drzewostan.features.presentation.modules.forestStand

import android.os.Bundle
import com.anioncode.drzewostan.R
import com.anioncode.drzewostan.core.base.BaseFragment
import com.anioncode.drzewostan.features.presentation.modules.forestStand.view_models.TreesPagerViewModel
import com.anioncode.drzewostan.databinding.FragmentTreeLayoutBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class TreesPagerFragment : BaseFragment<TreesPagerViewModel, FragmentTreeLayoutBinding>(
    BR.viewModel,
    R.layout.fragment_tree_layout
) {

    override val viewModel: TreesPagerViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}