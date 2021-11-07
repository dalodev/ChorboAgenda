/*
 * Copyright 2021 dalodev
 */
package es.littledavity.features.splash

import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import es.littledavity.commons.ui.base.BaseActivity
import es.littledavity.commons.ui.base.events.Route
import es.littledavity.commons.ui.bindings.viewBinding
import es.littledavity.features.splash.databinding.ActivitySplashBinding

@AndroidEntryPoint
internal class SplashActivity : BaseActivity<
    ActivitySplashBinding,
    SplashViewModel,
    SplashNavigator>() {

    override val viewBinding by viewBinding(ActivitySplashBinding::inflate)
    override val viewModel by viewModels<SplashViewModel>()

    override fun onLoadData() {
        super.onLoadData()
        viewModel.init()
    }

    override fun onRoute(route: Route) {
        super.onRoute(route)
        when (route) {
            is SplashRoute.Dashboard -> navigator.goToDashBoard(this)
            is SplashRoute.Exit -> navigator.exitApp()
        }
    }
}
