/*
 * Copyright 2021 dalodev
 */
package es.littledavity.chorboagenda.dashboard

import com.paulrybitskyi.hiltbinder.BindType
import es.littledavity.features.add.AddContactFragment
import es.littledavity.features.contacts.ContactsFragment
import es.littledavity.features.dashboard.fragment.adapter.DashboardAdapterFragmentFactory
import es.littledavity.features.likes.LikedContactsFragment
import javax.inject.Inject

@BindType(installIn = BindType.Component.FRAGMENT)
internal class DashboardAdapterFragmentFactoryImpl @Inject constructor() : DashboardAdapterFragmentFactory {
    override fun contactsFragment() = ContactsFragment()
    override fun addFragment() = AddContactFragment()
    override fun likesFragment() = LikedContactsFragment()
}
