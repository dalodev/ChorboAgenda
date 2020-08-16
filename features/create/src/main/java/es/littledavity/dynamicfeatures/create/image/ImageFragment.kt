/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.create.image

import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts.GetContent
import androidx.navigation.fragment.navArgs
import es.littledavity.chorboagenda.ChorboagendaApp
import es.littledavity.commons.ui.base.BaseFragment
import es.littledavity.commons.ui.extensions.observe
import es.littledavity.dynamicfeatures.create.R
import es.littledavity.dynamicfeatures.create.databinding.FragmentImageBinding
import es.littledavity.dynamicfeatures.create.image.di.DaggerImageComponent
import es.littledavity.dynamicfeatures.create.image.di.ImageModule

/**
 * Chorbo image view containing bottom navigation bar with different chorbos tabs.
 *
 * @see BaseFragment
 */
class ImageFragment : BaseFragment<FragmentImageBinding, ImageViewModel>(
    layoutId = R.layout.fragment_image
) {

    private val args: ImageFragmentArgs by navArgs()

    private val getContent = registerForActivityResult(GetContent()) { uri ->
        uri?.let(viewModel::loadImage)
    }

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
        observe(viewModel.state, ::onViewStateChange)
    }

    /**
     * Initialize dagger injection dependency graph.
     */
    override fun onInitDependencyInjection() {
        DaggerImageComponent
            .builder()
            .coreComponent(ChorboagendaApp.coreComponent(requireContext()))
            .imageModule(ImageModule(this))
            .build()
            .inject(this)
    }

    /**
     * Initialize view data binding variables.
     */
    override fun onInitDataBinding() {
        viewBinding.viewModel = viewModel
        viewBinding.nameTitle.text = args.name
        viewBinding.toolbar.setNavigationOnClickListener { viewModel.back() }
    }

    override fun onClear() {}

    /**
     * Observer view state change on [ImageViewModel].
     *
     * @param viewState State of image fragment view.
     */
    private fun onViewStateChange(viewState: ImageViewState) {
        when (viewState) {
            is ImageViewState.OpenGallery -> getContent.launch("image/*")
        }
    }

    /**
     * Observer view event change on [ImageViewModel].
     *
     * @param viewEvent Event on chorbos list.
     */
    private fun onViewEvent(viewEvent: ImageViewEvent) {
        when (viewEvent) {
            is ImageViewEvent.Next -> {
                viewModel.navigate(
                    ImageFragmentDirections.toLocation(
                        args.name,
                        viewModel.imageUri.value.toString()
                    ), null
                )
            }
        }
    }
}
