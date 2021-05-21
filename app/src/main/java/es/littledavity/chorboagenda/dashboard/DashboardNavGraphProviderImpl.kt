package es.littledavity.chorboagenda.dashboard

import com.paulrybitskyi.hiltbinder.BindType
import es.littledavity.chorboagenda.R
import es.littledavity.features.dashboard.DashboardNavGraphProvider
import javax.inject.Inject

@BindType(installIn = BindType.Component.ACTIVITY)
internal class DashboardNavGraphProviderImpl @Inject constructor(
) : DashboardNavGraphProvider {
    override fun getNavGraphId(): Int = R.navigation.dashboard_graph
}