/*
 * Copyright 2021 dalodev
 */
package es.littledavity.features.dashboard

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import es.littledavity.commons.ui.animations.WindowAnimations
import es.littledavity.commons.ui.extensions.intentFor
import es.littledavity.commons.ui.extensions.overrideEnterTransition
import javax.inject.Inject

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity(R.layout.activity_dashboard) {

    companion object {
        fun newIntent(context: Context) = context.intentFor<DashboardActivity>()
    }

    @Inject
    lateinit var navGraphProvider: DashboardNavGraphProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        overrideEnterTransition(WindowAnimations.FADING_ANIMATIONS)
        initSystemUiVisibility()
        initNavGraph()
    }

    private fun initSystemUiVisibility() {
        // To be able to draw behind system bars & change their colors
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }

    private fun initNavGraph() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val graph = navController.navInflater.inflate(navGraphProvider.getNavGraphId())

        navController.graph = graph
    }
}
