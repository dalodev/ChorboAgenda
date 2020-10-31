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

class ImageFragment : BaseFragment<FragmentImageBinding, ImageViewModel>(
    layoutId = R.layout.fragment_image
) {

    private val args: ImageFragmentArgs by navArgs()

    private val getContent = registerForActivityResult(GetContent()) { uri ->
        uri?.let(viewModel::loadImage)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.event, ::onViewEvent)
        observe(viewModel.state, ::onViewStateChange)
    }

    override fun onInitDependencyInjection() {
        DaggerImageComponent
            .builder()
            .coreComponent(ChorboagendaApp.coreComponent(requireContext()))
            .imageModule(ImageModule(this))
            .build()
            .inject(this)
    }

    override fun onInitDataBinding() {
        viewBinding?.viewModel = viewModel
        viewBinding?.nameTitle?.text = args.name
    }

    override fun onClearView() = Unit
    override fun toolbar() = viewBinding?.toolbar?.toolbar

    /**
     * Observer view state change on [ImageViewModel].
     *
     * @param viewState State of image fragment view.
     */
    private fun onViewStateChange(viewState: ImageViewState) {
        when (viewState) {
            is ImageViewState.OpenGallery -> getContent.launch("image/*")
            else -> Unit
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
                    ),
                    null
                )
            }
        }
    }
}
