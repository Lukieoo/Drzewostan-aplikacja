package com.anioncode.drzewostan.features.domain.di

import com.anioncode.drzewostan.features.TreesDataRepository
import com.anioncode.drzewostan.features.data.repository.TreesDataRepositoryImpl
import com.anioncode.drzewostan.features.domain.GetTreesDataUseCase
import com.anioncode.drzewostan.features.naviagation.TreesDataNavigator
import com.anioncode.drzewostan.features.naviagation.TreesDataNavigatorImpl
import com.anioncode.drzewostan.features.presentation.modules.forestStand.ForestStandFragment
import com.anioncode.drzewostan.features.presentation.modules.forestStand.view_models.ForestStandViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val treesDataModule = module {

    // data
    factory<TreesDataRepository> { TreesDataRepositoryImpl(get()) }
    // domain
    factory { GetTreesDataUseCase(get()) }
    // navigation
    factory<TreesDataNavigator> { TreesDataNavigatorImpl(get()) }
    // presentation
    viewModel { ForestStandViewModel() }
    factory { ForestStandFragment() }
//    viewModel { TreesDataDetailsViewModel() }
//    factory { TreesDataDetailsFragment() }
}