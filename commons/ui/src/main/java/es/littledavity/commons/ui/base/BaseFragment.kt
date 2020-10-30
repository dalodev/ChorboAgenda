/*
 * Copyright 2020 littledavity
 */
package es.littledavity.commons.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import es.littledavity.commons.ui.R
import es.littledavity.commons.ui.extensions.observe
import es.littledavity.commons.ui.navigation.NavigationCommand
import kotlinx.coroutines.cancel
import javax.inject.Inject

abstract class BaseFragment<B : ViewDataBinding, M : BaseViewModel>(
    @LayoutRes
    private val layoutId: Int
) : Fragment() {

    @Inject
    lateinit var viewModel: M
    var viewBinding: B? = null
    private val binding
        get() = viewBinding!!


    /**
     * Called to initialize dagger injection dependency graph when fragment is attached.
     */
    abstract fun onInitDependencyInjection()

    /**
     * Called to Initialize view data binding variables when fragment view is created.
     */
    abstract fun onInitDataBinding()

    /**
     * Called when destroy view to clear observers and listeners.
     */
    abstract fun onClearView()

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
        viewBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        viewBinding?.lifecycleOwner = viewLifecycleOwner
        sharedElementEnterTransition =
            TransitionInflater.from(this.context).inflateTransition(R.transition.shared_transition)
        sharedElementReturnTransition =
            TransitionInflater.from(this.context).inflateTransition(R.transition.shared_transition)
        return binding.root
    }

    /**
     * Called when a fragment is first attached to its context.
     *
     * @param context The application context.
     *
     * @see Fragment.onAttach
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        onInitDependencyInjection()
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
        onInitDataBinding()
        observe(viewModel.navigationCommands, ::onNavigate)
    }

    /**
     * Return the [AppCompatActivity] this fragment is currently associated with.
     *
     * @throws IllegalStateException if not currently associated with an activity or if associated
     * only with a context.
     * @throws TypeCastException if the currently associated activity don't extend [AppCompatActivity].
     *
     * @see requireActivity
     */
    fun requireCompatActivity(): AppCompatActivity {
        requireActivity()
        val activity = requireActivity()
        if (activity is AppCompatActivity) {
            return activity
        } else {
            throw TypeCastException("Main activity should extend from 'AppCompatActivity'")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null
        viewModel.viewModelScope.cancel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onClearView()
        viewBinding = null
    }

    private fun onNavigate(command: NavigationCommand) {
        when (command) {
            is NavigationCommand.To ->
                command.extras?.let { findNavController().navigate(command.directions, it) }
                    ?: findNavController().navigate(command.directions)
            is NavigationCommand.BackTo -> findNavController().popBackStack(
                command.destinationId,
                false
            )
            is NavigationCommand.Back -> findNavController().popBackStack()
            is NavigationCommand.ToRoot -> findNavController().popBackStack(
                R.id.chorbo_list_fragment,
                false
            )
        }
    }
}
