package com.anioncode.drzewostan.core.di

import android.content.Context
import android.net.ConnectivityManager
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import com.anioncode.drzewostan.R
import com.anioncode.drzewostan.core.navigation.FragmentNavigator
import com.anioncode.drzewostan.core.navigation.FragmentNavigatorImpl
import com.anioncode.drzewostan.core.provider.ActivityProvider
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    factory { LinearLayoutManager(androidContext(), LinearLayoutManager.VERTICAL, false) }
    factory { androidContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager }
    single(createdAtStart = true) { ActivityProvider(androidApplication()) }

    factory<FragmentNavigator> {
        FragmentNavigatorImpl(
            activityProvider = get(),
            navHostFragmentRes = R.id.nav_host_fragment,
            homeDestinationRes = R.id.forestStandFragment,
            defaultNavOptions = get()
        )
    }
    factory {
        navOptions {}

    }
}