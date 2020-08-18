/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.create.contact

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import es.littledavity.chorboagenda.ChorboagendaApp
import es.littledavity.commons.ui.base.BaseFragment
import es.littledavity.commons.ui.extensions.observe
import es.littledavity.dynamicfeatures.create.R
import es.littledavity.dynamicfeatures.create.contact.di.ContactModule
import es.littledavity.dynamicfeatures.create.contact.di.DaggerContactComponent
import es.littledavity.dynamicfeatures.create.databinding.FragmentContactBinding

/**
 * Chorbo name view containing bottom navigation bar with different chorbos tabs.
 *
 * @see BaseFragment
 */
class ContactFragment : BaseFragment<FragmentContactBinding, ContactViewModel>(
    layoutId = R.layout.fragment_contact
) {

    private val args: ContactFragmentArgs by navArgs()

    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * @param view The view returned by onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     * @see BaseFragment.onViewCreated
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.event, ::onViewEvent)
        observe(viewModel.state, ::onViewState)
        viewModel.setData(args)
    }

    /**
     * Initialize dagger injection dependency graph.
     */
    override fun onInitDependencyInjection() {
        DaggerContactComponent
            .builder()
            .coreComponent(ChorboagendaApp.coreComponent(requireContext()))
            .contactModule(ContactModule(this))
            .build()
            .inject(this)
    }

    /**
     * Initialize view data binding variables.
     */
    override fun onInitDataBinding() {
        viewBinding.viewModel = viewModel
        val countryCode = "+${args.countryCode}"
        viewBinding.countryCode.text = countryCode
        viewBinding.toolbar.setNavigationOnClickListener { viewModel.back() }
    }

    override fun onClear() = Unit

    /**
     * Observer view state change on [ContactViewModel].
     *
     * @param viewState Stte on chorbos list.
     */
    private fun onViewState(viewState: ContactViewState) {
        when (viewState) {
            is ContactViewState.Error ->
                Snackbar.make(
                    viewBinding.root,
                    viewState.message.orEmpty(),
                    Snackbar.LENGTH_SHORT
                ).show()
        }
    }

    /**
     * Observer view event change on [ContactViewModel].
     *
     * @param viewEvent Event on chorbos list.
     */
    private fun onViewEvent(viewEvent: ContactViewEvent) {
        when (viewEvent) {
            is ContactViewEvent.Next -> viewModel.navigate(ContactFragmentDirections.toList())
        }
    }
}
