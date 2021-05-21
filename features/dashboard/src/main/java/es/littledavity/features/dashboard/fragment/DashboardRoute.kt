package es.littledavity.features.dashboard.fragment

import es.littledavity.commons.ui.base.events.Route

internal sealed class DashboardRoute : Route {

    object Search : DashboardRoute()

}