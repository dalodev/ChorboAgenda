/*
 * Copyright 2021 dev.id
 */
package es.littledavity.features.dashboard.fragment

import dagger.hilt.android.lifecycle.HiltViewModel
import es.littledavity.commons.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor() : BaseViewModel() {

    fun onToolbarRightButtonClicked() {
        route(DashboardRoute.Search)
    }

    fun onAddContactButtonClicked() {
        route(DashboardRoute.Add)
    }
}
