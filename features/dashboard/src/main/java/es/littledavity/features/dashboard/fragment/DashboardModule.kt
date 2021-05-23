/*
 * Copyright 2021 dev.id
 */
package es.littledavity.features.dashboard.fragment

import androidx.fragment.app.Fragment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import es.littledavity.features.dashboard.fragment.adapter.DashboardAdapterFragmentFactory
import es.littledavity.features.dashboard.fragment.adapter.DashboardViewPagerAdapter

@Module
@InstallIn(FragmentComponent::class)
internal object DashboardModule {

    @Provides
    fun provideDashboardViewPagerAdapter(
        fragment: Fragment,
        fragmentFactory: DashboardAdapterFragmentFactory
    ) = DashboardViewPagerAdapter(
        fragment = fragment,
        fragmentFactory = fragmentFactory
    )
}
