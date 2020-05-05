/*
 * Copyright 2020 littledavity
 */
package es.littledavity.chorboagenda

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import es.littledavity.commons.ui.navigation.NavigationResult
import kotlin.properties.Delegates

/**
 * Base activity class that use the support library action bar features.
 *
 * @see AppCompatActivity
 */
class MainActivity : AppCompatActivity() {

    /**
     * Called when the activity is starting. This is where most initialization should go: calling
     * [AppCompatActivity.setContentView] to inflate the activity's UI, using
     * [AppCompatActivity.findViewById] to programmatically interact with widgets in the UI.
     *
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     * @see AppCompatActivity.onCreate
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun navigateBackWithResult(result: Bundle) {
        val childFragmentManager = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)?.childFragmentManager
        var backStackListener: FragmentManager.OnBackStackChangedListener by Delegates.notNull()
        backStackListener = FragmentManager.OnBackStackChangedListener {
            (childFragmentManager?.fragments?.get(0) as NavigationResult).onNavigationResult(result)
            childFragmentManager.removeOnBackStackChangedListener(backStackListener)
        }
        childFragmentManager?.addOnBackStackChangedListener(backStackListener)
        findNavController(R.id.main_graph).popBackStack()
    }
}
