package es.littledavity.dynamicfeatures.create.image

import es.littledavity.chorboagenda.ChorboagendaApp
import es.littledavity.commons.ui.base.BaseFragment
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
    }

    override fun onClear() {
    }
}