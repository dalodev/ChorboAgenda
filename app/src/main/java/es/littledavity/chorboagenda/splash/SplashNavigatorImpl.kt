package es.littledavity.chorboagenda.splash

import android.app.Activity
import android.content.Context
import com.paulrybitskyi.hiltbinder.BindType
import es.littledavity.features.dashboard.DashboardActivity
import es.littledavity.features.splash.SplashNavigator
import javax.inject.Inject

@BindType(installIn = BindType.Component.ACTIVITY)
internal class SplashNavigatorImpl @Inject constructor(
    private val activity: Activity
) : SplashNavigator {

    override fun goToDashBoard(context: Context) {
        activity.startActivity(DashboardActivity.newIntent(context))
        activity.finish()
    }

    override fun exitApp() {
        activity.finish()
    }
}