/*
 * Copyright 2021 dev.id
 */
package es.littledavity.chorboagenda.dashboard

import com.paulrybitskyi.hiltbinder.BindType
import es.littledavity.features.contacts.ContactsFragment
import es.littledavity.features.dashboard.fragment.adapter.DashboardAdapterFragmentFactory
import javax.inject.Inject

@BindType(installIn = BindType.Component.FRAGMENT)
internal class DashboardAdapterFragmentFactoryImpl @Inject constructor(
) : DashboardAdapterFragmentFactory {
    override fun contactsFragment() = ContactsFragment()
}
