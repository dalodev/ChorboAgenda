/*
 * Copyright 2021 dev.id
 */
package es.littledavity.commons.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.Toolbar
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.viewbinding.ViewBinding
import com.paulrybitskyi.commons.ktx.showLongToast
import com.paulrybitskyi.commons.ktx.showShortToast
import es.littledavity.commons.ui.base.events.Command
import es.littledavity.commons.ui.base.events.GeneralCommand
import es.littledavity.commons.ui.base.navigation.Navigator
import es.littledavity.commons.ui.base.events.Route
import es.littledavity.commons.ui.extensions.observeIn
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

abstract class BaseFragment<
        VB : ViewBinding,
        VM : BaseViewModel,
        NA : Navigator
        >(
    @LayoutRes private val layoutId: Int
) : Fragment(layoutId) {

    private var isViewCreated = false

    protected abstract val viewBinding: VB
    protected abstract val viewModel: VM

    @Inject
    lateinit var navigator: NA

    protected var enableBack = true

    /**
     * @return toolbar from fragment
     */
    open fun toolbar(): Toolbar? = null

    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container If non-null, this is the parent view that the fragment's UI should be
     * attached to. The fragment should not add the view itself, but this can be used to generate
     * the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous
     * saved state as given here.
     *
     * @return Return the View for the fragment's UI, or null.
     *
     * @see Fragment.onCreateView
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return if (isViewCreated) {
//            viewBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
//            viewBinding.lifecycleOwner = viewLifecycleOwner
            viewBinding.root
        } else {
            super.onCreateView(inflater, container, savedInstanceState)
        }

    }

    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * @param view The view returned by onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     * @see Fragment.onViewCreated
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val wasViewCreated = isViewCreated
        isViewCreated = true

        if (!wasViewCreated) {
            onPreInit()
            onInit()
            onPostInit()
        }

        onBindViewModel()

        if (!wasViewCreated) {
            savedInstanceState?.let(::onRestoreState)
        }
    }

    @CallSuper
    protected open fun onPreInit() = Unit // Stub


    @CallSuper
    protected open fun onInit() = Unit // Stub

    @CallSuper
    protected open fun onPostInit() {
        loadData()
    }

    private fun loadData() {
        lifecycleScope.launchWhenResumed {
            onLoadData()
        }
    }

    protected open fun onLoadData() = Unit // Stub

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
    protected open fun onHandleCommand(command: Command) {
        when (command) {
            is GeneralCommand.ShowShortToast -> showShortToast(command.message)
            is GeneralCommand.ShowLongToast -> showLongToast(command.message)
        }
    }

    @CallSuper
    protected open fun onRoute(route: Route) = Unit // Stub

    @CallSuper
    protected open fun onRestoreState(state: Bundle) = Unit // Stub

    final override fun onSaveInstanceState(state: Bundle) {
        onSaveState(state)
        super.onSaveInstanceState(state)
    }

    @CallSuper
    protected open fun onSaveState(state: Bundle) = Unit // Stub

    override fun onDestroy() {
        super.onDestroy()
        isViewCreated = false
        viewModel.viewModelScope.cancel()
    }
}
