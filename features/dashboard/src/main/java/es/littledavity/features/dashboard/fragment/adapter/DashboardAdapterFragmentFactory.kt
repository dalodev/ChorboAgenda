/*
 * Copyright 2021 dalodev
 */
package es.littledavity.features.dashboard.fragment.adapter

import androidx.fragment.app.Fragment

interface DashboardAdapterFragmentFactory {
    fun contactsFragment(): Fragment
    fun addFragment(): Fragment
    fun likesFragment(): Fragment
}
