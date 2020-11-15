/*
 * Copyright 2020 littledavity
 */
package es.littledavity.chorboagenda

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.ActivityNavigator
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import es.littledavity.chorboagenda.databinding.ActivityMainBinding
import es.littledavity.commons.ui.base.BaseActivity
import es.littledavity.commons.ui.navigation.NavigationResult
import kotlin.properties.Delegates

/**
 * Base activity class that use the support library action bar features.
 *
 * @see AppCompatActivity
 */
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val navController by lazy { findNavController(R.id.nav_host_fragment) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupToolbar(viewBinding?.activityToolbar)
    }

    override fun onSupportNavigateUp() = navController.navigateUp()

    fun navigateBackWithResult(result: Bundle) {
        val childFragmentManager =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment)?.childFragmentManager
        var backStackListener: FragmentManager.OnBackStackChangedListener by Delegates.notNull()
        backStackListener = FragmentManager.OnBackStackChangedListener {
            (childFragmentManager?.fragments?.get(0) as NavigationResult).onNavigationResult(result)
            childFragmentManager.removeOnBackStackChangedListener(backStackListener)
        }
        childFragmentManager?.addOnBackStackChangedListener(backStackListener)
        findNavController(R.id.main_graph).popBackStack()
    }
}
