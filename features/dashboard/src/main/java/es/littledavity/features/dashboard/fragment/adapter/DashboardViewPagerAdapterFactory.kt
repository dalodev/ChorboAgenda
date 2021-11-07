/*
 * Copyright 2021 dalodev
 */
package es.littledavity.features.dashboard.fragment.adapter

import androidx.fragment.app.Fragment
import com.paulrybitskyi.hiltbinder.BindType
import javax.inject.Inject

internal interface DashboardViewPagerAdapterFactory {
    fun createAdapter(fragment: Fragment): DashboardViewPagerAdapter
}

@BindType(installIn = BindType.Component.FRAGMENT)
internal class DashboardViewPagerAdapterFactoryImpl @Inject constructor(
    private val fragmentFactory: DashboardAdapterFragmentFactory
) : DashboardViewPagerAdapterFactory {

    override fun createAdapter(fragment: Fragment) = DashboardViewPagerAdapter(
        fragment = fragment,
        fragmentFactory = fragmentFactory
    )
}
