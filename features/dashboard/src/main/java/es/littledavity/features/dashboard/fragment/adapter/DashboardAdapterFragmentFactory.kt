/*
 * Copyright 2021 dev.id
 */
package es.littledavity.features.dashboard.fragment.adapter

import androidx.fragment.app.Fragment

interface DashboardAdapterFragmentFactory {
    fun contactsFragment(): Fragment
    fun likesFragment(): Fragment
}
