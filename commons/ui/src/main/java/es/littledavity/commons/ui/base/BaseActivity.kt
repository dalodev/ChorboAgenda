/*
 * Copyright 2021 dev.id
 */
package es.littledavity.commons.ui.base

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import es.littledavity.commons.ui.base.events.Command
import es.littledavity.commons.ui.base.events.GeneralCommand
import es.littledavity.commons.ui.base.navigation.Navigator
import es.littledavity.commons.ui.base.events.Route
import es.littledavity.commons.ui.extensions.observeIn
import es.littledavity.commons.ui.extensions.showLongToast
import es.littledavity.commons.ui.extensions.showShortToast
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

abstract class BaseActivity<
        VB : ViewBinding,
        VM : BaseViewModel,
        NA : Navigator
        > : AppCompatActivity() {

    protected abstract val viewBinding: VB
    protected abstract val viewModel: VM

    @Inject
    lateinit var navigator: NA

    var activityToolbar: Toolbar? = null

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
        onPreInit()
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        onInit()
        onPostInit()
    }

    @CallSuper
    protected open fun onPreInit() {
        // Stub
    }

    @CallSuper
    protected open fun onInit() {
        onBindViewModel()
    }

    @CallSuper
    protected open fun onBindViewModel() {
        bindViewModelCommands()
        bindViewModelRoutes()
    }

    private fun bindViewModelCommands() {
        viewModel.commandFlow
            .onEach(::onHandleCommand)
            .observeIn(this)
    }

    private fun bindViewModelRoutes() {
        viewModel.routeFlow
            .onEach(::onRoute)
            .observeIn(this)
    }

    @CallSuper
    protected open fun onPostInit() {
        loadData()
    }

    private fun loadData() {
        lifecycleScope.launchWhenResumed {
            onLoadData()
        }
    }

    protected open fun onLoadData() {
        // Stub
    }

    @CallSuper
    protected open fun onHandleCommand(command: Command) {
        when (command) {
            is GeneralCommand.ShowShortToast -> showShortToast(command.message)
            is GeneralCommand.ShowLongToast -> showLongToast(command.message)
        }
    }

    @CallSuper
    protected open fun onRoute(route: Route) {
        // Stub
    }

    final override fun onRestoreInstanceState(state: Bundle) {
        super.onRestoreInstanceState(state)

        onRestoreState(state)
    }

    @CallSuper
    protected open fun onRestoreState(state: Bundle) {
        // Stub
    }

    final override fun onSaveInstanceState(state: Bundle) {
        onSaveState(state)
        super.onSaveInstanceState(state)
    }

    @CallSuper
    protected open fun onSaveState(state: Bundle) {
        // Stub
    }
}
