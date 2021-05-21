/*
 * Copyright 2021 dev.id
 */
package es.littledavity.features.dashboard.fragment.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import es.littledavity.features.dashboard.fragment.DashboardPage
import es.littledavity.features.dashboard.fragment.DashboardPage.Companion.toDashboardPageFromPosition

internal class DashboardViewPagerAdapter(
    fragment: Fragment,
    private val fragmentFactory: DashboardAdapterFragmentFactory
) : FragmentStateAdapter(fragment) {


    override fun createFragment(position: Int): Fragment {
        return when(position.toDashboardPageFromPosition()) {
            DashboardPage.CONTACTS -> fragmentFactory.contactsFragment()
            //TODO add bottom nav navigations
        }
    }


    override fun getItemCount(): Int {
        return DashboardPage.values().size
    }

}
