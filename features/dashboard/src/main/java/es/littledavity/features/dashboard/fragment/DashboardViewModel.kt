package es.littledavity.features.dashboard.fragment

import es.littledavity.commons.ui.base.BaseViewModel
import javax.inject.Inject

class DashboardViewModel @Inject constructor() : BaseViewModel() {

    fun onToolbarRightButtonClicked() {
        route(DashboardRoute.Search)
    }
}