package es.littledavity.features.dashboard

import androidx.annotation.NavigationRes

interface DashboardNavGraphProvider {

    @NavigationRes
    fun getNavGraphId(): Int
}