/*
 * Copyright 2021 dev.id
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


    override fun createAdapter(fragment: Fragment): DashboardViewPagerAdapter {
        return DashboardViewPagerAdapter(
            fragment = fragment,
            fragmentFactory = fragmentFactory
        )
    }
}