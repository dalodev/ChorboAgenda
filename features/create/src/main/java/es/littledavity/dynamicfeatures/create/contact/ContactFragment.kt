/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.create.contact

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import es.littledavity.chorboagenda.ChorboagendaApp
import es.littledavity.commons.ui.base.BaseFragment
import es.littledavity.commons.ui.extensions.observe
import es.littledavity.dynamicfeatures.create.R
import es.littledavity.dynamicfeatures.create.contact.di.ContactModule
import es.littledavity.dynamicfeatures.create.contact.di.DaggerContactComponent
import es.littledavity.dynamicfeatures.create.databinding.FragmentContactBinding

class ContactFragment : BaseFragment<FragmentContactBinding, ContactViewModel>(
    layoutId = R.layout.fragment_contact
) {

    private val args: ContactFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.event, ::onViewEvent)
        observe(viewModel.state, ::onViewState)
        viewModel.setData(args)
    }

    override fun onInitDependencyInjection() {
        DaggerContactComponent
            .builder()
            .coreComponent(ChorboagendaApp.coreComponent(requireContext()))
            .contactModule(ContactModule(this))
            .build()
            .inject(this)
    }

    override fun onInitDataBinding() {
        viewBinding?.viewModel = viewModel
        val countryCode = "+${args.countryCode}"
        viewBinding?.countryCode?.text = countryCode
        viewBinding?.name?.text = args.name
    }

    override fun onClearView() = Unit
    override fun toolbar() = viewBinding?.toolbar?.toolbar

    /**
     * Observer view state change on [ContactViewModel].
     *
     * @param viewState Stte on chorbos list.
     */
    private fun onViewState(viewState: ContactViewState) {
        when (viewState) {
            is ContactViewState.Error ->
                Snackbar.make(
                    viewBinding!!.root,
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
