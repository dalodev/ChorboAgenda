/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.chorboList.detail

import es.littledavity.commons.ui.base.BaseFragment
import es.littledavity.dynamicfeatures.chorbo_list.R
import es.littledavity.dynamicfeatures.chorbo_list.databinding.FragmentChorboDetailBinding

/**
 * View detail for selected chorbo, displaying extra info.
 *
 * @see BaseFragment
 */
class ChorboDetailFragment : BaseFragment<FragmentChorboDetailBinding, ChorboDetailViewModel>(
    layoutId = R.layout.fragment_chorbo_detail
) {
    override fun onInitDependencyInjection() = Unit

    override fun onInitDataBinding() = Unit

    override fun onClear() = Unit
}
